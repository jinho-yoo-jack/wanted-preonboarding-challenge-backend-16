package com.wanted.preonboarding.ticket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class CancelReservationRequestDto {
    private UUID performanceId;
    private char line;
    private int seat;
    private String reservationName;
    private String reservationPhoneNumber;
}
