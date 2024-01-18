package com.wanted.preonboarding.ticket.domain.dto;

import java.util.UUID;

public record RequestNotification(
        UUID performanceId,
        int round,
        String email

) {
}
