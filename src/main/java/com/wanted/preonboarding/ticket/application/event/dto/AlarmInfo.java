package com.wanted.preonboarding.ticket.application.event.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AlarmInfo(
        UUID performanceId,
        Long userId,
        int round,
        char line,
        int gate,
        int seat
) {

    public static AlarmInfo of(Long userId, Reservation reservation) {
        return AlarmInfo.builder()
                .performanceId(reservation.getPerformanceId())
                .userId(userId)
                .round(reservation.getRound())
                .line(reservation.getLine())
                .gate(reservation.getGate())
                .seat(reservation.getSeat())
                .build();
    }
}
