package com.wanted.preonboarding.ticket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CancelReserveRequest {

    private UUID performanceId;
    private String reservationName;
    private String reservationPhoneNumber;
    private int round;



}
