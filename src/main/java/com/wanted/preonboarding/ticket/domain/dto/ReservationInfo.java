package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private Integer reservationId;
    private PerformanceInfo performanceInfo;
    private UserInfo userInfo;
    private Integer amount;
    private Integer gate;
    private Character line;
    private Integer seat;

    public static ReservationInfo of(Reservation entity) {
        return ReservationInfo.builder()
                .reservationId(entity.getId())
                .performanceInfo(PerformanceInfo.builder()
                        .performanceId(entity.getPerformanceId())
                        .build())
                .userInfo(UserInfo.builder()
                        .userId(entity.getUserId())
                        .build())
                .gate(entity.getGate())
                .line(entity.getLine())
                .seat(entity.getSeat())
                .build();
    }

    public static ReservationInfo of(Reservation reservation, PerformanceInfo performanceInfo, UserInfo userInfo) {
        return ReservationInfo.builder()
                .reservationId(reservation.getId())
                .performanceInfo(performanceInfo)
                .userInfo(userInfo)
                .gate(reservation.getGate())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .build();
    }
}
