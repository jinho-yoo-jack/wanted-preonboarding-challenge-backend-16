package com.wanted.preonboarding.ticketing.domain.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReadReservationRequest {
    @NotBlank
    private final String reservationName;
    @NotBlank
    private final String phoneNumber;
}
