package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@Builder
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;
    private String reservationStatus; // 예약; 취소;
    private long amount;
    private int round;
    private int gate;
    private char line;
    private int seat;

    public static ReserveInfo of(Reservation info){
        return ReserveInfo.builder()
                .performanceId(info.getPerformanceId())
                .reservationName(info.getName())
                .reservationPhoneNumber(info.getPhoneNumber())
                .reservationStatus(info.getStatus())
                .amount(info.getAmount())
                .round(info.getRound())
                .gate(1)
                .line(info.getLine())
                .seat(info.getSeat())
                .build();
    }
}
