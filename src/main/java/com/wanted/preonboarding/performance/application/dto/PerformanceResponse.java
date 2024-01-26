package com.wanted.preonboarding.performance.application.dto;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class PerformanceResponse {

    private final String performanceName;

    private final int round;

    private final Date startDate;

    private final String isReserve;

    public static PerformanceResponse from(Performance performance) {
        return PerformanceResponse.builder()
                .performanceName(performance.getName())
                .round(performance.getRound())
                .startDate(performance.getStartDate())
                .isReserve(performance.getReserveState())
                .build();
    }
}
