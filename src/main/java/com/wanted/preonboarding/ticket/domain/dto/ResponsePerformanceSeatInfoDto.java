package com.wanted.preonboarding.ticket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponsePerformanceSeatInfoDto {
    private char seatLine;
    private int seatNumber;
}
