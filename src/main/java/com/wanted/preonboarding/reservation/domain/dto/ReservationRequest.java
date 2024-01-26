package com.wanted.preonboarding.reservation.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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

    @NotNull
    @Positive
    private int round;

    @NotNull
    @Positive
    private int gate;

    @NotNull
    private char line;

    @NotNull
    @Positive
    private int seat;
}
