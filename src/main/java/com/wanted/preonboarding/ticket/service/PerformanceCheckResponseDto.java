package com.wanted.preonboarding.ticket.service;

import java.time.LocalDateTime;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;

public record PerformanceCheckResponseDto(String performanceName,
                                          int round,
                                          LocalDateTime startDate,
                                          ReservationStatus status) {

    public static PerformanceCheckResponseDto of(final Performance performance) {
        return new PerformanceCheckResponseDto(performance.getName(),
                                               performance.getRound(),
                                               performance.getStart_date(),
                                               performance.getStatus());
    }
}
