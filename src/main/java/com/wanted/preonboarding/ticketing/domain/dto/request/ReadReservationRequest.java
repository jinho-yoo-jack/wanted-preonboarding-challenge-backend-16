package com.wanted.preonboarding.ticketing.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReadReservationRequest {
    private final String reservationName;
    private final String phoneNumber;
}
