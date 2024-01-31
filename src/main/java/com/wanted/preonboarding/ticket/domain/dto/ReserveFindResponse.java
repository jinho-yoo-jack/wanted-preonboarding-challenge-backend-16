package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReserveFindResponse {
    private UUID performanceId;
    private String performanceName;
    private int round;
    private int seat;
    private String line;

    //예매자 정보
    private String reservationName;
    private String phoneNumber;
}
