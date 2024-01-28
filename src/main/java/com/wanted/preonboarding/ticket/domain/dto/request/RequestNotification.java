package com.wanted.preonboarding.ticket.domain.dto.request;

import java.util.UUID;

public record RequestNotification(
        UUID performanceId,
        int round,
        String email

) {
}
