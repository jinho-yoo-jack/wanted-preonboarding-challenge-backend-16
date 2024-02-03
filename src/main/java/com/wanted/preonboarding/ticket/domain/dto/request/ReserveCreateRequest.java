package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReserveCreateRequest {
    private UUID performanceId;
    private String ReservationName; //고객 이름
    private String ReservationPhoneNumber;
    private int balance;
    private int round;
    private int seat;
    private char line;
}
