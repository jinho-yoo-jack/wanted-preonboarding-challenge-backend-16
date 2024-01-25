package com.wanted.preonboarding.ticketing.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CancelReservationResponse {
    private final UUID performanceId;
    private final String performanceName;
    private final LocalDateTime startedTime;
    private final int round;
    private final int gate;
    private final String line;
    private final int seat;
    private final String isReserve;
}
