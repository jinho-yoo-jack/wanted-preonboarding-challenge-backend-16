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
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class ReservationAlarm {

    private static final String START = "예약 대기한 공연에 빈 좌석이 나와 안내드립니다.";
    private static final String PERFORMANCE_ID = "\n공연ID : ";
    private static final String PERFORMANCE_NAME = "\n공연명 : ";
    private static final String ROUND = "\n회차 : ";
    private static final String START_DATE = "\n시작일시 : ";
    private static final String SEAT_INFO = "\n좌석 정보 : ";
    private static final String KOREA_LOCALE = "+082";
    private static final int FILTERED_LENGTH = 1;
    private static final int FILTERED_INDEX = 0;
    private static final Map<Performance, List<UserInfo>> waitingMap = new HashMap<>();

    private final TwilioProperties twilioProperties;

    @EventListener(WaitToReserveEvent.class)
    public void addWaitingUser(final WaitToReserveEvent waiting) {
        if(containsPerformance(waiting.getPerformance())) {
            addNewUserInfo(waiting);
            return;
        }
        addNewWaitingData(waiting);
    }

    @EventListener(CheckWaitingEvent.class)
    public void deleteWaitingIfExist(final CheckWaitingEvent event) {
        List<Performance> filteredList =
                waitingMap.keySet().stream()
                        .filter(performance -> performance.equalsId(event.getPerformanceId()))
                        .toList();
        if(filteredList.size() != FILTERED_LENGTH) return;
        if(!waitingMap.get(filteredList.get(FILTERED_INDEX)).contains(event.getUserInfo())) return;
        waitingMap.get(filteredList.get(FILTERED_INDEX)).remove(event.getUserInfo());
    }

    @EventListener(ReservationCanceledEvent.class)
    public void sendMessageToWaiting(final ReservationCanceledEvent reservationCanceledEvent) {
        waitingMap.keySet().stream()
                .filter(performance -> performance.equalsId(reservationCanceledEvent.getPerformanceId()))
                .forEach(performance -> sendWaitingsMessage(reservationCanceledEvent, performance));
    }

    private void sendWaitingsMessage(final ReservationCanceledEvent event, Performance performance) {
        waitingMap.get(performance)
                .forEach(userInfo -> {
                    String message = createVacantSeatMessage(event, performance);
                    sendNotification(userInfo.getPhoneNumber(), message);
                });
    }

    private boolean containsPerformance(final Performance performance) {
        return waitingMap.containsKey(performance);
    }

    private void addNewWaitingData(final WaitToReserveEvent waiting) {
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(waiting.getUserInfo());
        waitingMap.put(waiting.getPerformance(), userInfos);
    }

    private void addNewUserInfo(final WaitToReserveEvent waiting) {
        waitingMap.get(waiting.getPerformance()).add(waiting.getUserInfo());
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
        return START
                + PERFORMANCE_ID + performance.getId()
                + PERFORMANCE_NAME + performance.getName()
                + ROUND + performance.getRound()
                + START_DATE + performance.getStartDate().toString()
                + SEAT_INFO + seatInfo.toString();
    }
}
