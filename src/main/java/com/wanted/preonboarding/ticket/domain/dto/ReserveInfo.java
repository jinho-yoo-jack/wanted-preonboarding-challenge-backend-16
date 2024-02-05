package com.wanted.preonboarding.ticket.domain.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record ReserveInfo(UUID performanceId, String reservationName, String reservationPhoneNumber, long amount,
                          int round, char line, int seat) {
}
