package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReserveSelectRequest {
    private String reservationName;
    private String reservationPhoneNumber;
}
