package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReservationInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private PerformanceInfo performanceInfo;
    private UserInfo userInfo;
    private Integer amount;
    private Integer gate;
    private Character line;
    private Integer seat;

    public static ReservationInfo of(Reservation reservation, PerformanceInfo performanceInfo, UserInfo userInfo) {
        return ReservationInfo.builder()
                .performanceInfo(performanceInfo)
                .userInfo(userInfo)
                .gate(reservation.getGate())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .build();
    }
}
