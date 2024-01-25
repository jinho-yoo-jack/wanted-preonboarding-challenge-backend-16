package com.wanted.preonboarding.reservation.application.dto;

import com.querydsl.core.annotations.QueryProjection;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

public class ReservationResponse {

    private final int round;

    private final String performanceName;

    private final UUID performanceId;

    private final SeatInfo seatInfo;

    private final UserInfo userInfo;

    @QueryProjection
    @Builder
    public ReservationResponse(int round, String performanceName, UUID performanceId, SeatInfo seatInfo, UserInfo userInfo) {
        this.round = round;
        this.performanceName = performanceName;
        this.performanceId = performanceId;
        this.seatInfo = seatInfo;
        this.userInfo = userInfo;
    }

    public static ReservationResponse from(final Performance performance, final Reservation reservation) {
        return ReservationResponse.builder()
                .performanceId(performance.getId())
                .performanceName(performance.getName())
                .round(performance.getRound())
                .seatInfo(reservation.getSeatInfo())
                .userInfo(reservation.getUserInfo())
                .build();
    }
}
