package com.wanted.preonboarding.reservation.infrastructure.externalServices;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.reservation.domain.event.CheckWaitingEvent;
import com.wanted.preonboarding.reservation.domain.event.ReservationCanceledEvent;
import com.wanted.preonboarding.reservation.domain.event.WaitToReserveEvent;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import com.wanted.preonboarding.reservation.infrastructure.configuration.TwilioProperties;
import com.wanted.preonboarding.reservation.infrastructure.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ReservationAlarm {
    private static final String KOREA_LOCALE = "+082";
    private static final String MESSAGE_FORMAT =
            """
            예약 대기한 공연에 빈 좌석이 나와 안내드립니다.
            공연ID : %s
            공연명 : %s
            회차 : %d
            시작일시 : %s
            좌석 정보 : %s
            """;

    private final TwilioProperties twilioProperties;
    private final AlarmRepository alarmRepository;

    @EventListener(WaitToReserveEvent.class)
    public void addWaitingUser(final WaitToReserveEvent waiting) {
        alarmRepository.addWaiting(waiting);
    }

    @EventListener(CheckWaitingEvent.class)
    public void deleteWaitingIfExist(final CheckWaitingEvent event) {
        alarmRepository.deleteWaiting(event);
    }


    @EventListener(ReservationCanceledEvent.class)
    public void sendMessageToWaiting(final ReservationCanceledEvent reservationCanceledEvent) {

        alarmRepository.findWaitingByPerformanceId(reservationCanceledEvent)
                .forEach(performance -> sendWaitingsMessage(reservationCanceledEvent, performance));
    }

    private void sendWaitingsMessage(final ReservationCanceledEvent event, Performance performance) {
        alarmRepository.findUsersByPerformance(performance)
                .forEach(userInfo -> {
                    String message = createVacantSeatMessage(event, performance);
                    sendNotification(userInfo.getPhoneNumber(), message);
                });
    }

    private void sendNotification(final String phoneNumber, final String message) {
        Twilio.init(twilioProperties.getUsername(), twilioProperties.getPassword());
        String receivingNumber = KOREA_LOCALE + phoneNumber;
        Message.creator(
                new PhoneNumber(receivingNumber),
                new PhoneNumber(twilioProperties.getPhone()),
                message)
                .create();
    }

    private String createVacantSeatMessage(final ReservationCanceledEvent event, final Performance performance) {
        SeatInfo seatInfo = event.getSeatInfo();
        return String.format(
                MESSAGE_FORMAT,
                performance.getId(),
                performance.getName(),
                performance.getRound(),
                performance.getStartDate().toString(),
                seatInfo.toString()
                );
    }
}
