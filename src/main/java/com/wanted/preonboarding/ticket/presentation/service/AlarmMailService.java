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
import com.wanted.preonboarding.ticket.presentation.ConcreteReserveStrategy;
import com.wanted.preonboarding.ticket.presentation.ReserveStrategy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
public class AlarmMailService {

    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final AlarmRepository alarmRepository;
    private final ReserveStrategy reserveStrategy;

    public AlarmMailService(PerformanceRepository performanceRepository,
                            PerformanceSeatInfoRepository performanceSeatInfoRepository,
                            AlarmRepository alarmRepository,
                            ConcreteReserveStrategy concreteReserveStrategy) {
        this.performanceRepository = performanceRepository;
        this.performanceSeatInfoRepository = performanceSeatInfoRepository;
        this.alarmRepository = alarmRepository;
        this.reserveStrategy = concreteReserveStrategy;
    }

    @Transactional
    public void createAlarmPerformanceSeat(CreateAlarmPerformanceSeatRequest dto) {

        Performance performance = getPerformance(dto.getPerformanceId());

        //1. 알림 가능한 공연인지 확인
        reserveStrategy.isReserveEnable(performance.getIsReserve());

        //2. 알림 가능한 공연 좌석인지 확인
        List<PerformanceSeatInfo> performanceSeatInfoList = getPerformanceSeat(dto.getPerformanceId(), performance.getRound(), ReserveStatus.DISABLE);

        for (PerformanceSeatInfo performanceSeatInfo : performanceSeatInfoList) {
            Alarm alarm = Alarm.of(performanceSeatInfo, dto);
            alarmRepository.save(alarm);
        }

    }

    @Transactional
    public void sendAlarmPerformanceSeat(CreateAlarmPerformanceSeatRequest dto) {
        //TODO 저장한 알람 가져와서 보내면 됨. Performance, PerformanceInfo 안가져와도 됨
        Alarm alarm = getAlarm(dto);
        Performance performance = getPerformance(dto.getPerformanceId());

        List<PerformanceSeatInfo> performanceSeatInfoList = getPerformanceSeat(dto.getPerformanceId(), performance.getRound(), ReserveStatus.CANCEL);

        List<SendMessagePerformanceSeat> sendMessagePerformanceSeatList = null;
        for (PerformanceSeatInfo performanceSeatInfo : performanceSeatInfoList) {
            SendMessagePerformanceSeat sendMessagePerformanceSeat = SendMessagePerformanceSeat.of(performance, performanceSeatInfo);
            sendMessagePerformanceSeatList.add(sendMessagePerformanceSeat);
        }

        if(sendMessagePerformanceSeatList != null) {
            messageBody(dto.getReservationEmail(), sendMessagePerformanceSeatList);
        }
    }

    private Alarm getAlarm(CreateAlarmPerformanceSeatRequest dto) {
        return alarmRepository.findByPerformanceIdAndNameAndPhoneNumberAndEmail(
                dto.getPerformanceId(),
                dto.getReservationName(),
                dto.getReservationPhoneNumber(),
                dto.getReservationEmail())
                .orElseThrow(() -> new ServiceException(StatusCode.NOT_FOUND));
    }


    public void messageBody(String reservationEmail, List<SendMessagePerformanceSeat> dto) {
        Session session = getSession(this.username, this.password, getProperties());

        sendMessageBody(session, reservationEmail, dto);
    }

    private void sendMessageBody(Session session, String reservationEmail, List<SendMessagePerformanceSeat> dto) {
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

    private List<PerformanceSeatInfo> getPerformanceSeat(UUID performanceId, int round, ReserveStatus reserveStatus) {
        return performanceSeatInfoRepository.findByPerformanceIdAndRound(performanceId, round, reserveStatus.getValue());
    }

    private Performance getPerformance(UUID performanceId) {
        return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new ServiceException(StatusCode.NOT_FOUND));
    }


    private String createMessage(List<SendMessagePerformanceSeat> dto) {
        String message = null;
        for (SendMessagePerformanceSeat performanceSeat : dto) {
            message += String.format("공연ID: %s\n공연명: %s\n회차: %s\n시작 일시: %s\n예매 가능한 좌석 정보: Gate: %s, Line: %s, Seat: %s\n\n",
                    performanceSeat.getPerformanceId(), performanceSeat.getPerformanceName(),
                    performanceSeat.getRound(), performanceSeat.getStartDate(), performanceSeat.getGate(), performanceSeat.getLine(), performanceSeat.getSeat());
        }
        return message;
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
