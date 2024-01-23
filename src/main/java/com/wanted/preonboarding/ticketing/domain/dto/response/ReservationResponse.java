package com.wanted.preonboarding.ticketing.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class ReservationResponse {
    private String performanceName;
    private UUID performanceId;
    private int changes;
    private int round;
    private int gate;
    private String line;
    private int seat;
    private String reservationName;
    private String phoneNumber;
}
