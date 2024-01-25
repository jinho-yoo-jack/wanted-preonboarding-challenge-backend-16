package com.wanted.preonboarding.ticketing.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CancelReservationRequest {
    @NotBlank
    private final Long reservationId;
    @NotBlank
    private final Long reservationSeatId;
}
