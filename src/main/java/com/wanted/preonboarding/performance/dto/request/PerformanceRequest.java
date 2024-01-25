package com.wanted.preonboarding.performance.dto.request;

import com.wanted.preonboarding.performance.domain.constant.PerformanceType;
import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PerformanceRequest {
    private String performanceName;
    private Integer price;
    private Integer round;
    private int performanceType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reserveStatus;

    public PerformanceInfo toDto() {
        return PerformanceInfo.builder()
                .performanceName(performanceName)
                .price(price)
                .round(round)
                .performanceType(PerformanceType.of(performanceType))
                .startDate(startDate)
                .endDate(endDate)
                .reserveStatus(ReserveStatus.valueOf(reserveStatus))
                .build();
    }

}
