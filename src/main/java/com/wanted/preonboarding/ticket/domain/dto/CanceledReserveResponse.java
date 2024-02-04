package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CanceledReserveResponse {
    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;
    private int round;
    private char line;
    private int seat;
}
