package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@NoArgsConstructor
@Embeddable
@Getter
@Setter(AccessLevel.PROTECTED)
public class SeatInfo {
    @Column(nullable = false)
    private int round;
    private int gate;
    private char line;
    private int seat;

    @Builder
    public SeatInfo(int round, int gate, char line, int seat) {
        this.round = round;
        this.gate = gate;
        this.line = line;
        this.seat = seat;
    }
}
