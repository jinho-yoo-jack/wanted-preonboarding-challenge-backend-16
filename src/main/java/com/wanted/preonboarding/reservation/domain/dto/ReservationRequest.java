package com.wanted.preonboarding.reservation.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReservationRequest {

    @NotBlank
    private UUID performanceId;

    @NotBlank
    private String name;

    @NotBlank
    private String phoneNumber;

    @NotBlank
    private long account;

    @NotBlank
    private int round;

    @NotBlank
    private int gate;

    @NotBlank
    private char line;

    @NotBlank
    private int seat;
}
