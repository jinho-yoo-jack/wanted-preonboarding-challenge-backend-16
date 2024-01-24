package com.wanted.preonboarding.ticketing.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReadReservationResponse {
    private final UUID performanceId;
    private final String performanceName;
    private final int round;
    private int gate;
    private String line;
    private int seat;
    private final String reservationName;
    private final String phoneNumber;
}
