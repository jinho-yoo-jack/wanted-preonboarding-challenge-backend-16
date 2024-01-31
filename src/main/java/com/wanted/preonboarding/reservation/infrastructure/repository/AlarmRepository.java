package com.wanted.preonboarding.reservation.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.reservation.domain.event.CheckWaitingEvent;
import com.wanted.preonboarding.reservation.domain.event.ReservationCanceledEvent;
import com.wanted.preonboarding.reservation.domain.event.WaitToReserveEvent;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

@Component
@NoArgsConstructor
public class AlarmRepository {

    private static final int FILTERED_LENGTH = 1;
    private static final int FILTERED_INDEX = 0;

    private static final Map<Performance, List<UserInfo>> waitingMap = new HashMap<>();

    public void addWaiting(final WaitToReserveEvent waiting) {
        if(containsPerformance(waiting.getPerformance())) {
            addNewUserInfo(waiting);
            return;
        }
        addNewWaitingData(waiting);
    }

    public void deleteWaiting(final CheckWaitingEvent event) {
        List<Performance> filteredList =
                waitingMap.keySet().stream()
                        .filter(performance -> performance.equalsId(event.getPerformanceId()))
                        .toList();
        if(filteredList.size() != FILTERED_LENGTH) return;
        if(!containsUserInfo(event, filteredList.get(FILTERED_INDEX))) return;
        waitingMap.get(filteredList.get(FILTERED_INDEX)).remove(event.getUserInfo());
    }

    public Stream<Performance> findWaitingByPerformanceId(final ReservationCanceledEvent event) {
        return waitingMap.keySet().stream()
                .filter(performance -> performance.equalsId(event.getPerformanceId()));
    }

    public List<UserInfo> findUsersByPerformance(final Performance performance) {
        return waitingMap.get(performance);
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

    private boolean containsUserInfo(CheckWaitingEvent event, Performance performance) {
        return waitingMap.get(performance).contains(event.getUserInfo());
    }
}
