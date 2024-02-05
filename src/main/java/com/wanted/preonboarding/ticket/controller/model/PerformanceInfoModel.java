package com.wanted.preonboarding.ticket.controller.model;

import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.domain.PerformanceSeat;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceInfoModel {
    private int round;
    private String title;
    private String seat;
    private Long performanceId;


    public static PerformanceInfoModel of(Performance performance, PerformanceSeat performanceSeat) {
        return PerformanceInfoModel.builder()
                .round(performance.getRound())
                .title(performance.getTitle())
                .seat(performanceSeat.getSeat())
                .performanceId(performance.getId())
                .build();
    }
}
