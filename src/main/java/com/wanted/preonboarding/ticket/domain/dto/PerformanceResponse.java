package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PerformanceResponse {
    private UUID performanceId;
    private String performanceName;
    private String performanceType;
    private LocalDate startDate;
    private boolean isReserve;

    public static PerformanceResponse of(Performance entity) {
        return PerformanceResponse.builder()
            .performanceId(entity.getId())
            .performanceName(entity.getName())
            .performanceType(entity.getType().toString())
            .startDate(entity.getStart_date())
            .isReserve(entity.isReserve())
            .build();
    }

}
