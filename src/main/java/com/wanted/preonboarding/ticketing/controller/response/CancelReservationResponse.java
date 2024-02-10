package com.wanted.preonboarding.ticketing.controller.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CancelReservationResponse {
    private final Long seatId;
    private final String isReserve;
}
