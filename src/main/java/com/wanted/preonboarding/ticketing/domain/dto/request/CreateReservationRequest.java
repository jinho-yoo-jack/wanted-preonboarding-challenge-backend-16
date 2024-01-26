package com.wanted.preonboarding.ticketing.domain.dto.request;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotNull
    private Integer balance;
    @NotNull
    private UUID performanceId;
    @NotNull
    private Long seatId;
    @NotNull
    private Integer round;
    @NotNull
    private Integer gate;
    @NotBlank
    private String line;
    @NotNull
    private Integer seat;
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
