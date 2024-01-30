package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.UUID;

@Data
@Builder
public class PerformanceInfo {
    private UUID performanceId;
    private String performanceName;
    private String performanceType;
    private LocalDate startDate;
    private String isReserve;

    public static PerformanceInfo of(Performance entity) {
        return PerformanceInfo.builder()
            .performanceId(entity.getId())
            .performanceName(entity.getName())
            .performanceType(convertCodeToName(entity.getType()))
            .startDate(entity.getStart_date().toLocalDate())
            .isReserve(entity.getIsReserve())
            .build();
    }

    private static String convertCodeToName(int code){
        return Arrays.stream(PerformanceType.values()).filter(value -> value.getCategory() == code)
            .findFirst()
            .orElse(PerformanceType.NONE)
            .name();
    }

}
