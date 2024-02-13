package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReservationCreateRequest {
    private UUID performanceId;
    private String reservationName; //고객 이름
    private String reservationPhoneNumber;
    private Integer balance;
    private Integer round;
    private Integer seat;
    private Character line;
}
