package com.wanted.preonboarding.reservation.infrastructure.externalServices;

import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.reservation.domain.event.CheckWaitingEvent;
import com.wanted.preonboarding.reservation.domain.event.ReservationCanceledEvent;
import com.wanted.preonboarding.reservation.domain.event.WaitToReserveEvent;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ReservationAlarm {

    private static final Map<Performance, List<UserInfo>> waitingMap = new HashMap<>();

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
        if(!waitingMap.containsKey(event.getPerformance())) return;
        if(!waitingMap.get(event.getPerformance()).contains(event.getUserInfo())) return;
        waitingMap.get(event.getPerformance()).remove(event.getUserInfo());
    }

    @EventListener(ReservationCanceledEvent.class)
    public void sendMessageToWaiting(final ReservationCanceledEvent reservationCanceledEvent) {
        waitingMap.get(reservationCanceledEvent.getPerformance())
                .stream()
                .forEach(() -> {}); // 전송 로직 추가
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

    }

    private String createVacantSeatMessage(final Performance performance, final SeatInfo seatInfo) {
        return "예약 대기한 공연에 빈 좌석이 나와 안내드립니다."
                + "\n공연ID : " + performance.getId()
                + "\n공연명 : " + performance.getName()
                + "\n회차 : " + performance.getRound()
                + "\n시작일시 : " + performance.getStartDate().toString()
                + "\n좌석 정보 : " + seatInfo.toString();
    }
}
