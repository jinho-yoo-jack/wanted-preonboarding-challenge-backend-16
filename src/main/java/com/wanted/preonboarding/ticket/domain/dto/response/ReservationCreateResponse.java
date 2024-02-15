package com.wanted.preonboarding.ticket.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReservationCreateResponse {
    private Integer id;
    private UUID performanceId;
    private String performanceName;
    private int round;
    private int seat;
    private char line;

    //예매자 정보
    private String reservationName;
    private String reservationPhoneNumber;

}
