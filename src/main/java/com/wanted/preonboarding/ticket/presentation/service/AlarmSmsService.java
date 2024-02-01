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
import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import com.wanted.preonboarding.ticket.global.exception.ResultCode;
import com.wanted.preonboarding.ticket.global.exception.ServiceException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
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


    public BaseResDto performanceCancelCameout(ReservePossibleAlarmCustomerInfoDto dto) {

        isSendReserveExist(dto);

        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findByUUID(dto.getPerformanceId())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));

        Performance performance = performanceRepository.findById(dto.getPerformanceId())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));


        SendMessagePerformanceSeatInfoDto sendMessagePerformanceSeatInfoDto = SendMessagePerformanceSeatInfoDto.of(performanceSeatInfo);
        sendMessagePerformanceSeatInfoDto.setPerformanceName(performance.getName());
        sendMessagePerformanceSeatInfoDto.setStartDate(performance.getStart_date());

        return messageBody(dto.getReservationPhoneNumber(), sendMessagePerformanceSeatInfoDto);
    }

    private void isSendReserveExist(ReservePossibleAlarmCustomerInfoDto dto) {
        Reservation reservation = reservationRepository
                .findByNameAndPhoneNumber(dto.getReservationName(), dto.getReservationPhoneNumber())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));
    }


    public SmsResponse messageBody(String recipientPhoneNumber, SendMessagePerformanceSeatInfoDto dto) {
        log.info("SmsService messageBody");

        //Send Message: 공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보
        this.secondContentPart = createSecondContentPart(dto);

        Long time = System.currentTimeMillis();

        List<MessagesDto> messages = getMessagesDtos(recipientPhoneNumber);

        SmsRequest smsRequest = new SmsRequest("SMS", "COMM", "82", sendPhoneNo, "내용", messages);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonBody = getString(smsRequest, objectMapper);

        HttpHeaders headers = getHttpHeaders(time);

        HttpEntity<String> body = new HttpEntity<>(jsonBody, headers);

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory());

        return sendSms(restTemplate, body);
    }

    private static String getString(SmsRequest smsRequest, ObjectMapper objectMapper) {
        String jsonBody;
        try {
            jsonBody = objectMapper.writeValueAsString(smsRequest);
        } catch (JsonProcessingException e) {
            throw new ServiceException(ResultCode.JSON_PROCESSING);
        }
        return jsonBody;
    }

    private List<MessagesDto> getMessagesDtos(String recipientPhoneNumber) {
        List<MessagesDto> messages = new ArrayList<>();
        String sendContent = this.sendCompanyName + " " + this.firstContentPart + "\n" + this.secondContentPart;
        messages.add(new MessagesDto(recipientPhoneNumber, sendContent));
        return messages;
    }

    //TODO: 네이버클라우드플랫폼 개인계정 SENS 서비스 이용불가. 다른 방법 찾기
    @Transactional
    public SmsResponse sendSms(RestTemplate restTemplate, HttpEntity<String> body)  {
        log.info("SmsService sendSms");
        SmsResponse smsResponse;
        try {
            smsResponse = restTemplate.postForObject(new URI("https://sens.apigw.ntruss.com/sms/v2/services/" + this.serviceId + "/messages"), body, SmsResponse.class);
        } catch (URISyntaxException e) {
            throw new ServiceException(ResultCode.URI_SYNTAX);
        }
        return smsResponse;
    }

    private HttpHeaders getHttpHeaders(Long time) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-ncp-apigw-timestamp", time.toString());
        headers.set("x-ncp-iam-access-key", this.accessKey);

        String sig; //암호화
        sig = makeSignature(time);
        headers.set("x-ncp-apigw-signature-v2", sig);
        return headers;
    }

    private String createSecondContentPart(SendMessagePerformanceSeatInfoDto dto) {
        return String.format("%s %s\n공연ID: %s\n,공연명: %s\n,회차: %s\n, 시작 일시: %s\n,예매 가능한 좌석 정보: Gate: %s, Line: %s, Seat: %s",
                sendCompanyName, firstContentPart, dto.getPerformanceId(), dto.getPerformanceName(),
                dto.getRound(), dto.getStartDate(), dto.getGate(), dto.getLine(), dto.getSeat());
    }

    public String makeSignature(Long time)  {
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

        SecretKeySpec signingKey;
        try {
            signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException(ResultCode.UNSUPPORTED_ENCODING);
        }

        Mac mac;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            throw new ServiceException(ResultCode.NO_SUCH_ALGORITHM);
        }

        try {
            mac.init(signingKey);
        } catch (InvalidKeyException e) {
            throw new ServiceException(ResultCode.INVALID_KEY);
        }

        byte[] rawHmac;
        try {
            rawHmac = mac.doFinal(message.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new ServiceException(ResultCode.UNSUPPORTED_ENCODING);
        }

        return Base64.encodeBase64String(rawHmac);
    }

}
