package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class PerformanceResponse {
    private UUID performanceId;
    private String name;
    private Integer round;
    private String type;
    private LocalDateTime startDate;
    private Boolean isReserve;

    public static PerformanceResponse of(Performance entity) {
        return PerformanceResponse.builder()
                .performanceId(entity.getId())
                .name(entity.getName())
                .round(entity.getRound())
                .type(entity.getType().name())
                .startDate(entity.getStartDate())
                .isReserve(entity.getIsReserve())
                .build();
    }
}
