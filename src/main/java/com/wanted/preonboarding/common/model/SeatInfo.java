package com.wanted.preonboarding.common.model;

import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Embeddable
public class SeatInfo {

    private int round;

    private int gate;

    private char line;

    private int seat;

    public static SeatInfo from(ReservationRequest reservationRequest) {
        return SeatInfo.builder()
                .round(reservationRequest.getRound())
                .seat(reservationRequest.getSeat())
                .line(reservationRequest.getLine())
                .gate(reservationRequest.getGate())
                .build();
    }

    @Override
    public String toString() {
        return this.gate + " 게이트 " + this.line + this.seat;
    }
}
