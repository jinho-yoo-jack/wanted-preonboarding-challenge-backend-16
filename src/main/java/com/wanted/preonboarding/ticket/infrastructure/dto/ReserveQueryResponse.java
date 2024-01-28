package com.wanted.preonboarding.ticket.infrastructure.dto;

import java.util.UUID;

public record ReserveQueryResponse(
        UUID performanceId,
        String performanceName,
        int round,
        char line,
        int gate,
        int seat,
        String reservationName,
        String reservationPhoneNumber
) {
}
