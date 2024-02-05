package com.wanted.preonboarding.ticket.presentation.request;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateReserveRequest {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;
    private String reservationStatus; // 예약; 취소;
    private long amount;
    private int round;
    private char line;
    private int seat;
}
