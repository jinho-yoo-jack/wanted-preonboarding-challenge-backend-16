package com.wanted.preonboarding.performance.application.dto;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;
import java.util.UUID;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class PerformanceResponse {
    private final UUID performanceId;

    private final String performanceName;

    private final int round;

    private final Date startDate;

    private final String isReserve;

    public static PerformanceResponse from(Performance performance) {
        return PerformanceResponse.builder()
                .performanceId(performance.getId())
                .performanceName(performance.getName())
                .round(performance.getRound())
                .startDate(performance.getStartDate())
                .isReserve(performance.getReserveState())
                .build();
    }
}
