package com.wanted.preonboarding.ticket.application.dto;

import lombok.Builder;

@Builder
public record MessageInfo(
        String reservationName,
        String performanceName,
        String startDateTime,
        int round,
        char line,
        int seat,
        int gate
) {
}
