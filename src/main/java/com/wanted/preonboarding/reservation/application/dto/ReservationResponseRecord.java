package com.wanted.preonboarding.reservation.application.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ReservationResponseRecord(
        Integer round,
        String performanceName,
        UUID performanceId,
        SeatInfo seatInfo,
        UserInfo userInfo) {

    @QueryProjection
    public ReservationResponseRecord(final Reservation reservation, final Performance performance) {
        this(
                performance.getRound(),
                performance.getName(),
                performance.getId(),
                reservation.getSeatInfo(),
                reservation.getUserInfo()
        );
    }
}
