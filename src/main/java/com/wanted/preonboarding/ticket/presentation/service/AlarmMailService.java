package com.wanted.preonboarding.ticket.presentation.service;

import com.wanted.preonboarding.ticket.aop.ResultCode;
import com.wanted.preonboarding.ticket.aop.exception.ServiceException;
import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.AlarmRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AlarmMailService {

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final AlarmRepository alarmRepository;

    private static final String CANCEL = "cancel";
    private static final String DISABLE = "disable";
    private static final String ENABLE = "enable";

    @Transactional
    public void createAlarmPerformance(ReservePossibleAlarmCustomerInfoDto dto) {

        Performance performance = getPerformance(dto);
        if(performance.getIsReserve().equalsIgnoreCase(ENABLE)) {
            PerformanceSeatInfo performanceSeatInfo = getPerformanceSeatInfoAndStatus(dto, DISABLE);
            log.info("1");
            Alarm alarm = Alarm.of(performanceSeatInfo, dto);
            log.info("2");
            alarmRepository.save(alarm);
        } else {
            throw new ServiceException(ResultCode.BAD_REQUEST);
        }

    }

    @Transactional
    public void sendAlarmPerformance(ReservePossibleAlarmCustomerInfoDto dto) {

        Performance performance = getPerformance(dto);
        PerformanceSeatInfo performanceSeatInfo = getPerformanceSeatInfoAndStatus(dto, CANCEL);

        SendMessagePerformanceSeatInfoDto sendMessagePerformanceSeatInfoDto = SendMessagePerformanceSeatInfoDto.from(performanceSeatInfo);
        sendMessagePerformanceSeatInfoDto.updatePerformanceName(performance.getName());
        sendMessagePerformanceSeatInfoDto.updateStartDate(performance.getStart_date());

        messageBody(dto.getReservationEmail(), sendMessagePerformanceSeatInfoDto);
    }


    public void messageBody(String reservationEmail, SendMessagePerformanceSeatInfoDto dto) {
        log.info("SmsService messageBody");
        String username = this.username;
        String password = this.password;

        Properties prop = getProperties();
        Session session = getSession(username, password, prop);

        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(reservationEmail));

            // Mail Subject
            message.setSubject("예약 가능 좌석 알림");

            // Mail Message: 공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보
            message.setText(createMessage(dto));

            // Send the message
            Transport.send(message);
        } catch (AddressException e) {
            throw new ServiceException(ResultCode.EMAIL_ADDRESS_INVALID);
        } catch (MessagingException e) {
            throw new ServiceException(ResultCode.EMAIL_SENDING);
        }
    }
    private PerformanceSeatInfo getPerformanceSeatInfoAndStatus(ReservePossibleAlarmCustomerInfoDto dto, String status) {
        return performanceSeatInfoRepository.findByPerformanceIdAndIsReserve(dto.getPerformanceId(), status)
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));
    }

    private Performance getPerformance(ReservePossibleAlarmCustomerInfoDto dto) {
        return performanceRepository.findById(dto.getPerformanceId())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));
    }


    private String createMessage(SendMessagePerformanceSeatInfoDto dto) {
        return String.format("공연ID: %s\n공연명: %s\n회차: %s\n시작 일시: %s\n예매 가능한 좌석 정보: Gate: %s, Line: %s, Seat: %s",
                dto.getPerformanceId(), dto.getPerformanceName(),
                dto.getRound(), dto.getStartDate(), dto.getGate(), dto.getLine(), dto.getSeat());
    }

    private static Session getSession(String username, String password, Properties prop) {
        return Session.getDefaultInstance(prop, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    private static Properties getProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", 465);
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }
}
