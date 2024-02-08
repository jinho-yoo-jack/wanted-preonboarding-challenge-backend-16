package com.wanted.preonboarding.ticket.controller.model;

import com.wanted.preonboarding.ticket.domain.Performance;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSelectModel {
    private Long performanceId;
    private String title;
    private int round;
    private LocalDate startDate;
    private boolean isReserve;

    public static PerformanceSelectModel from(Performance performance) {
        return PerformanceSelectModel.builder()
                .performanceId(performance.getId())
                .title(performance.getTitle())
                .round(performance.getRound())
                .startDate(performance.getStartDate())
                .isReserve(performance.isReserve())
                .build();
    }
}
