package com.wanted.preonboarding.ticket.interfaces.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationResponseDto {

    private UUID performanceId;
    private int round;
    private String performanceName;
    private String line;
    private int seat;
    private String reservationHolderName;
    private String reservationHolderPhoneNumber;
    private LocalDateTime reservationDate;

    public static ReservationResponseDto newInstance(Reservation reservation) {
        return ReservationResponseDto.builder()
                .performanceId(reservation.getPerformance().getId().getId())
                .round(reservation.getPerformance().getId().getRound())
                .performanceName(reservation.getPerformance().getName())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .reservationHolderName(reservation.getReservationHolderName())
                .reservationHolderPhoneNumber(reservation.getPhoneNumber())
                .reservationDate(reservation.getCreatedAt())
                .build();
    }
}
