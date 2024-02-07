package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class ResponseReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId; //공연ID
    private String performanceName; //공연명
    private String reservationName;
    private String reservationPhoneNumber;
    private ResponsePerformanceSeatInfoDto seatInfo;
    private int round;
    private int gate;
    private long reservationAmount;

    @Builder
    private ResponseReserveInfo(UUID performanceId, String performanceName, String reservationName, String reservationPhoneNumber, ResponsePerformanceSeatInfoDto seatInfo, int round, int gate, long reservationAmount) {
        this.performanceId = performanceId;
        this.performanceName = performanceName;
        this.reservationName = reservationName;
        this.reservationPhoneNumber = reservationPhoneNumber;
        this.seatInfo = seatInfo;
        this.round = round;
        this.gate = gate;
        this.reservationAmount = reservationAmount;
    }

    public static ResponseReserveInfo of(Reservation reservation, PerformanceSeatInfo performanceSeatInfo){
        return ResponseReserveInfo.builder()
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .performanceName(performanceSeatInfo.getPerformance().getName())
                .performanceId(performanceSeatInfo.getPerformance().getId())
                .round(reservation.getRound())
                .seatInfo(performanceSeatInfo.convertResponseDto())
                .gate(reservation.getGate())
                .reservationAmount(reservation.getAmount())
                .build();
    }

}
