package com.wanted.preonboarding.ticket.presentation.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.ticket.domain.dto.MessagesDto;
import com.wanted.preonboarding.ticket.domain.dto.SmsRequest;
import com.wanted.preonboarding.ticket.domain.dto.SmsResponse;
import com.wanted.preonboarding.ticket.domain.dto.ReservePossibleAlarmCustomerInfoDto;
import com.wanted.preonboarding.ticket.domain.dto.SendMessagePerformanceSeatInfoDto;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmSmsService {
    @Value("${sms.service-id}")
    private String serviceId;
    @Value("${sms.access-key}")
    private String accessKey;
    @Value("${sms.secret-key}")
    private String secretKey;
    @Value("${sms.send-phone}")
    private String sendPhoneNo;
    @Value("${sms.send-company-name}")
    private String sendCompanyName;
    @Value("${sms.first-content-part}")
    private String firstContentPart;
    @Value("${sms.second-content-part}")
    private String secondContentPart;

    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;


    public SmsResponse performanceCancelCameout(ReservePossibleAlarmCustomerInfoDto reservePossibleAlarmCustomerInfoDto) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {

        isSendReserveExist(reservePossibleAlarmCustomerInfoDto);

        Optional<PerformanceSeatInfo> optionalPerformanceSeatInfo = performanceSeatInfoRepository.findByUUID(reservePossibleAlarmCustomerInfoDto.getPerformanceId());

        if(!optionalPerformanceSeatInfo.isPresent()) {
            throw new EntityNotFoundException();
        }
        PerformanceSeatInfo performanceSeatInfo = optionalPerformanceSeatInfo.get();


        Optional<Performance> optionalPerformance = performanceRepository.findById(reservePossibleAlarmCustomerInfoDto.getPerformanceId());
        if(!optionalPerformance.isPresent()) {
            throw new EntityNotFoundException();
        }
        Performance performance = optionalPerformance.get();


        SendMessagePerformanceSeatInfoDto sendMessagePerformanceSeatInfoDto = SendMessagePerformanceSeatInfoDto.of(performanceSeatInfo);
        sendMessagePerformanceSeatInfoDto.setPerformanceName(performance.getName());
        sendMessagePerformanceSeatInfoDto.setStartDate(performance.getStart_date());

        //알림 보내기
        SmsResponse smsResponse = sendSms(reservePossibleAlarmCustomerInfoDto.getReservationPhoneNumber(), sendMessagePerformanceSeatInfoDto);

        return smsResponse;
    }

    private void isSendReserveExist(ReservePossibleAlarmCustomerInfoDto reservePossibleAlarmCustomerInfoDto) {
        Optional<Reservation> optionalReservation = reservationRepository.findByNameAndPhoneNumber(reservePossibleAlarmCustomerInfoDto.getReservationName(), reservePossibleAlarmCustomerInfoDto.getReservationPhoneNumber());
        if(!optionalReservation.isPresent()) {
            throw new EntityNotFoundException();
        }
    }


    //TODO: 네이버클라우드플랫폼 개인계정 SENS 서비스 이용불가. 다른 방법 찾기
    @Transactional
    public SmsResponse sendSms(String recipientPhoneNumber, SendMessagePerformanceSeatInfoDto sendMessagePerformanceSeatInfoDto) throws JsonProcessingException, UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException, URISyntaxException {
        log.info("SmsService sendSms");

        //Send Message: 공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보
        this.secondContentPart = "공연ID: " + sendMessagePerformanceSeatInfoDto.getPerformanceId() +
        ", 공연명: " + sendMessagePerformanceSeatInfoDto.getPerformanceName() +
        ", 회차: " + sendMessagePerformanceSeatInfoDto.getRound() +
        ", 시작 일시: " + sendMessagePerformanceSeatInfoDto.getStartDate() +
        ", 예매 가능한 좌석 정보: Gate: " + sendMessagePerformanceSeatInfoDto.getGate() +
        ", Line: " + sendMessagePerformanceSeatInfoDto.getLine() +
        ", Seat: " + sendMessagePerformanceSeatInfoDto.getSeat();


        Long time = System.currentTimeMillis();
        List<MessagesDto> messages = new ArrayList<>();
        String sendContent = sendCompanyName + " " + firstContentPart + "\n" + secondContentPart;
        messages.add(new MessagesDto(recipientPhoneNumber, sendContent));

        SmsRequest smsRequest = new SmsRequest("SMS", "COMM", "82", sendPhoneNo, "내용", messages);
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = objectMapper.writeValueAsString(smsRequest);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", this.accessKey);

        String sig = makeSignature(time); //암호화
        headers.set("x-ncp-apigw-signature-v2", sig);

        HttpEntity<String> body = new HttpEntity<>(jsonBody,headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());
        SmsResponse smsResponse = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + this.serviceId + "/messages"), body, SmsResponse.class);

        return smsResponse;

    }
    public String makeSignature(Long time) throws UnsupportedEncodingException, NoSuchAlgorithmException, InvalidKeyException {
        log.info("AlarmSmsService makeSignature");
        String space = " ";
        String newLine = "\n";
        String method = "POST";
        String url = "/sms/v2/services/" + this.serviceId + "/messages";
        String timestamp = time.toString();
        String accessKey = this.accessKey;
        String secretKey = this.secretKey;

        String message = new StringBuilder()
                .append(method)
                .append(space)
                .append(url)
                .append(newLine)
                .append(timestamp)
                .append(newLine)
                .append(accessKey)
                .toString();

        SecretKeySpec signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(signingKey);

        byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        String encodeBase64String = Base64.encodeBase64String(rawHmac);

        return encodeBase64String;
    }

}
