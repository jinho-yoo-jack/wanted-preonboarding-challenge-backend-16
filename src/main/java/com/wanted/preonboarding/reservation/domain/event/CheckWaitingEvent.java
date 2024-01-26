package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class CheckWaitingEvent {

    UserInfo userInfo;
    Performance performance;

    public static CheckWaitingEvent from(final Reservation reservation) {
        return CheckWaitingEvent.builder()
                .userInfo(reservation.getUserInfo())
                .performance(reservation.getPerformance())
                .build();
    }
}
