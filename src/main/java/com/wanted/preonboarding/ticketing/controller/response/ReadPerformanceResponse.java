package com.wanted.preonboarding.ticketing.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ReadPerformanceResponse {
    private final String performanceName;
    private final int round;
    private final LocalDateTime startDate;
    private final String isReserve;
}
