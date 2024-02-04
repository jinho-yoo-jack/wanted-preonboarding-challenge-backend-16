package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationFindRequest {
    private String reservationName;
    private String reservationPhoneNumber;
}
