package com.wanted.preonboarding.performance.dto;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import com.wanted.preonboarding.performance.domain.constant.PerformanceType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Data
@Builder
public class PerformanceInfo {
    private UUID performanceId;
    private String performanceName;
    private Integer price;
    private Integer round;
    private PerformanceType performanceType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ReserveStatus reserveStatus;

    public static PerformanceInfo of(Performance entity) {
        return PerformanceInfo.builder()
                .performanceId(entity.getId())
                .performanceName(entity.getName())
                .price(entity.getPrice())
                .round(entity.getRound())
                .performanceType(entity.getType())
                .startDate(entity.getStartDate())
                .endDate(entity.getEndDate())
                .reserveStatus(entity.getReserveStatus())
                .build();
    }

    public Performance toEntity() {
        return Performance.builder()
                .id(performanceId)
                .name(performanceName)
                .price(price)
                .round(round)
                .type(performanceType)
                .startDate(startDate)
                .endDate(endDate)
                .reserveStatus(reserveStatus)
                .build();
    }

}
