package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatInfo {
    private Integer round;
    private Integer gate;
    private String line;
    private Integer seat;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SeatInfo seatInfo = (SeatInfo) o;
        return round.equals(seatInfo.getRound()) && gate.equals(seatInfo.getGate()) && line.equals(seatInfo.getLine()) && seat.equals(seatInfo.getSeat());
    }

    @Override
    public int hashCode() {
        return Objects.hash(round, gate, line, seat);
    }
}
