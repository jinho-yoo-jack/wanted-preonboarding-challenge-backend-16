package com.wanted.preonboarding.ticket.service.dto.response;

import java.util.UUID;

import lombok.Builder;

@Builder
public record ReservationResponseDto(UUID performanceId, String performanceName, String reservationName,
                                     String reservationPhoneNumber, int amount, int round, char line, int seat) {
}
