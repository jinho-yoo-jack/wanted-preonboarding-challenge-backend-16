package com.wanted.preonboarding.ticketing.domain.dto.request;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CreateReservationRequest {
    private String reservationName;
    private String phoneNumber;
    private int balance;
    private UUID performanceId;
    private Long seatId;
    private int round;
    private int gate;
    private String line;
    private int seat;
    private String reservationStatus;

    public Reservation fromTicket(Performance performance) {
        return Reservation.builder()
                .name(reservationName)
                .phoneNumber(phoneNumber)
                .performance(performance)
                .round(round)
                .gate(gate)
                .line(line)
                .seat(seat)
                .build();
    }
}
