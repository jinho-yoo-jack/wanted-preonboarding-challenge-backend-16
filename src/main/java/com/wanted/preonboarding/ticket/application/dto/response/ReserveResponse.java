package com.wanted.preonboarding.ticket.application.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;

@Builder
public record ReserveResponse(
        String performanceId,
        String performanceName,
        int round,
        char line,
        int seat,
        String reservationName,
        String reservationPhoneNumber
) {

    public static ReserveResponse of(Performance performance, Reservation reservation) {
        return ReserveResponse.builder()
                .performanceId(performance.getId().toString())
                .performanceName(performance.getName())
                .round(reservation.getRound())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .build();
    }
}
