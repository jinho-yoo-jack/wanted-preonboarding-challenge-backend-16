package com.wanted.preonboarding.ticketing.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CancelReservationRequest {
    private final Long reservationId;
    private final Long reservationSeatId;
}
