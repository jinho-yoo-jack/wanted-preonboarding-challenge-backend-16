package com.wanted.preonboarding.ticket.service.dto.response;

import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;

import lombok.Builder;

@Builder
public record ReservationCheckResponseDto(UUID performanceId, int round, char line, int seat, String performanceName,
                                          String reservationName, String reservationPhoneNumber) {
    public static ReservationCheckResponseDto of(final Reservation reservation) {
        return ReservationCheckResponseDto.builder()
                .performanceId(reservation.getPerformance().getId())
                .round(reservation.getRound())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .performanceName(reservation.getPerformance().getName())
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .build();
    }
}
