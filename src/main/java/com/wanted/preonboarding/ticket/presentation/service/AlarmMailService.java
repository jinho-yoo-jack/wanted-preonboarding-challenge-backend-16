package com.wanted.preonboarding.ticket.presentation.service;

import com.wanted.preonboarding.ticket.aop.StatusCode;
import com.wanted.preonboarding.ticket.aop.exception.ServiceException;
import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.dto.request.CreateAlarmPerformanceSeatRequest;
import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.global.common.ReserveStatus;
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

    @Transactional
    public void createAlarmPerformanceSeat(CreateAlarmPerformanceSeatRequest dto) {

        Performance performance = getPerformance(dto.getPerformanceId());

        //1. 알림 가능한 공연인지 확인
        performance.isReserve(ReserveStatus.ENABLE);

        //2. 알림 가능한 공연 좌석인지 확인
        PerformanceSeatInfo performanceSeatInfo = getPerformanceSeatInfoAndStatus(dto.getPerformanceId(), ReserveStatus.DISABLE.getValue());

        Alarm alarm = Alarm.of(performanceSeatInfo, dto);

        alarmRepository.save(alarm);
    }

    @Transactional
    public void sendAlarmPerformanceSeat(CreateAlarmPerformanceSeatRequest dto) {

        Alarm alarm = getAlarm(dto);
        Performance performance = getPerformance(dto.getPerformanceId());
        PerformanceSeatInfo performanceSeatInfo = getPerformanceSeatInfoAndStatus(dto.getPerformanceId(), ReserveStatus.CANCEL.getValue());

        SendMessagePerformanceSeat sendMessagePerformanceSeat = SendMessagePerformanceSeat.of(performance, performanceSeatInfo);

        messageBody(dto.getReservationEmail(), sendMessagePerformanceSeat);
    }

    private Alarm getAlarm(CreateAlarmPerformanceSeatRequest dto) {
        return alarmRepository.findByPerformanceIdAndNameAndPhoneNumberAndEmail(
                dto.getPerformanceId(),
                dto.getReservationName(),
                dto.getReservationPhoneNumber(),
                dto.getReservationEmail())
                .orElseThrow(() -> new ServiceException(StatusCode.NOT_FOUND));
    }


    public void messageBody(String reservationEmail, SendMessagePerformanceSeat dto) {
        Session session = getSession(this.username, this.password, getProperties());

        sendMessageBody(session, reservationEmail, dto);
    }

    private void sendMessageBody(Session session, String reservationEmail, SendMessagePerformanceSeat dto) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(this.username));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(reservationEmail));

            // Mail Subject
            message.setSubject("예약 가능 좌석 알림");

            // Mail Message: 공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보
            message.setText(createMessage(dto));

            // Send the message
            Transport.send(message);
        } catch (AddressException e) {
            throw new ServiceException(StatusCode.EMAIL_ADDRESS_INVALID);
        } catch (MessagingException e) {
            throw new ServiceException(StatusCode.EMAIL_SENDING);
        }
    }

    private PerformanceSeatInfo getPerformanceSeatInfoAndStatus(UUID performanceId, String status) {
        return performanceSeatInfoRepository.findByPerformanceIdAndIsReserve(performanceId, status)
                .orElseThrow(() -> new ServiceException(StatusCode.NOT_FOUND));
    }

    private Performance getPerformance(UUID performanceId) {
        return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new ServiceException(StatusCode.NOT_FOUND));
    }


    private String createMessage(SendMessagePerformanceSeat dto) {
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
