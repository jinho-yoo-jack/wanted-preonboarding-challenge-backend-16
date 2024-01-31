package com.wanted.preonboarding.ticket.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PerformanceFindResponse {
    private String performanceName;
    private int round;
    private LocalDateTime dateTime;
    private String isReserve;
}
