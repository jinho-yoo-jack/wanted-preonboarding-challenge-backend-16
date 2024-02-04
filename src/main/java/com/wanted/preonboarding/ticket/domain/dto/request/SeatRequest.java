package com.wanted.preonboarding.ticket.domain.dto.request;

import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SeatRequest {
    private int gate;
    private int round;
    private char line;
    private int seat;


    @Builder
    public SeatRequest(int gate, int round, char line, int seat) {
        this.gate = gate;
        this.round = round;
        this.line = line;
        this.seat = seat;
    }

    public SeatInfo toSeatInfo()
    {
        return SeatInfo.builder()
                .gate(this.gate)
                .round(this.round)
                .line(this.line)
                .seat(this.seat).build();
    }
}
