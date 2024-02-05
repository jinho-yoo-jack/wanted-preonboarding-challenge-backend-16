package com.wanted.preonboarding.ticket.presentation.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetAllPerformanceInfoListResponse {
    private String performanceName;
    private int round;
    private String startDate;
    private String isReserve;

    public static GetAllPerformanceInfoListResponse of(Performance performance) {
        return builder()
            .performanceName(performance.getName())
            .round(performance.getRound())
            .startDate(performance.getStart_date().toString())
            .isReserve(performance.getIsReserve())
            .build();
    }
}
