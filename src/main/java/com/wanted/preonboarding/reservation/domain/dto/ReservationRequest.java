package com.wanted.preonboarding.reservation.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationRequest {

    private UUID performanceId;

    private String name;

    private String phoneNumber;

    private long account;

    private int round;

    private int gate;

    private char line;

    private int seat;
}
