package com.wanted.preonboarding.ticketing.domain.dto.request;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateReservationRequest {
    @NotBlank
    private String reservationName;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private int balance;
    @NotBlank
    private UUID performanceId;
    @NotBlank
    private Long seatId;
    @NotBlank
    private int round;
    @NotBlank
    private int gate;
    @NotBlank
    private String line;
    @NotBlank
    private int seat;
    @NotBlank
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
