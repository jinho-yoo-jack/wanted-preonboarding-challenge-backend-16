package com.wanted.preonboarding.ticketing.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CancelReservationResponse {
    private final Long seatId;
    private final String isReserve;
}
