package com.wanted.preonboarding.ticket.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReservationCancelResponse {
    private UUID performanceId;
    private int round;
    private char line;
    private int seat;
}
