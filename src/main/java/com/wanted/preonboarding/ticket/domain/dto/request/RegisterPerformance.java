package com.wanted.preonboarding.ticket.domain.dto.request;

public record RegisterPerformance(
        String type,
        String name,
        Integer round,
        Integer price,
        String startDate,
        Integer totalSeat,
        Integer seatPerLine
) {
}

