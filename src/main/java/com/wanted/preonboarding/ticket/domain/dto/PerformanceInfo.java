package com.wanted.preonboarding.ticket.domain.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

import lombok.Builder;

@Builder
public record PerformanceInfo (UUID performanceId,
                               String performanceName,
                               String performanceType,
                               LocalDateTime startDate,
                               String reserveStatus) {
    public static PerformanceInfo of(final Performance entity) {
        return PerformanceInfo.builder()
                .performanceId(entity.getId())
                .performanceName(entity.getName())
                .performanceType(entity.getType().name())
                .startDate(entity.getStart_date())
                .reserveStatus(entity.getStatus().name())
                .build();
    }
}
