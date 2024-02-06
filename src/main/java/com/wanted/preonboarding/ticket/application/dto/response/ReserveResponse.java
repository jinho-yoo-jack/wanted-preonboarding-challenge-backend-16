package com.wanted.preonboarding.ticket.application.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.dto.ReserveQueryResponse;
import lombok.Builder;

@Builder
public record ReserveResponse(
        String performanceId,
        String performanceName,
        int round,
        char line,
        int gate,
        int seat,
        String reservationName,
        String reservationPhoneNumber
) {

    public static ReserveResponse of(Performance performance, Reservation reservation) {
        return ReserveResponse.builder()
                .performanceId(performance.getId().toString())
                .performanceName(performance.getName())
                .gate(reservation.getGate())
                .round(reservation.getRound())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .build();
    }

    public static ReserveResponse from(ReserveQueryResponse queryResponse) {
        return ReserveResponse.builder()
                .performanceId(queryResponse.performanceId().toString())
                .performanceName(queryResponse.performanceName())
                .gate(queryResponse.gate())
                .round(queryResponse.round())
                .line(queryResponse.line())
                .seat(queryResponse.seat())
                .reservationName(queryResponse.reservationName())
                .reservationPhoneNumber(queryResponse.reservationPhoneNumber())
                .build();
    }
}
