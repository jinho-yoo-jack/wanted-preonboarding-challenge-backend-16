package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class GetReservationRequestDto {
    private String reservationName;
    private String reservationPhoneNumber;
}