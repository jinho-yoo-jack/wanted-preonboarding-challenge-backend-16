package com.wanted.preonboarding.ticketing.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CancelReservationRequest {
    @NotNull
    private final Long reservationId;
    @NotNull
    private final Long reservationSeatId;
}
