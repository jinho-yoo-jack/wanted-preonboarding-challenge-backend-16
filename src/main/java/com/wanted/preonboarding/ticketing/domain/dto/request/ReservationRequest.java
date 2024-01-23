package com.wanted.preonboarding.ticketing.domain.dto.request;

import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class ReservationRequest {
    private String reservationName;
    private String phoneNumber;
    private Long balance;
    private UUID performanceId;
    private Long seatId;
    private int round;
    private int gate;
    private String line;
    private int seat;
    private String reservationStatus;

    public Reservation fromTicket() {
        return Reservation.builder()
                .name(reservationName)
                .phoneNumber(phoneNumber)
                .performanceId(performanceId)
                .round(round)
                .gate(gate)
                .line(line)
                .seat(seat)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
