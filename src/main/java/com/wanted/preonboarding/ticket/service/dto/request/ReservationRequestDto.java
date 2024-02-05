package com.wanted.preonboarding.ticket.service.dto.request;

import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;

import lombok.Builder;

@Builder
public record ReservationRequestDto(UUID performanceId, String reservationName, String reservationPhoneNumber,
                                    int amount, int round, char line, int seat) {
    public Reservation toEntity(final Performance performance) {
        final ReserveInfo reserveInfo = convertToReserveInfo();
        return Reservation.of(reserveInfo, performance);
    }

    private ReserveInfo convertToReserveInfo() {
        return ReserveInfo.builder()
                .performanceId(performanceId)
                .reservationName(reservationName)
                .reservationPhoneNumber(reservationPhoneNumber)
                .round(round)
                .line(line)
                .seat(seat)
                .build();
    }
}
