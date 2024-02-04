package com.wanted.preonboarding.ticket.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SeatInfoResponse{
    private int performanceSeatInfoId;
    private int round;
    private char line;
    private int seat;
    private int gate;
}