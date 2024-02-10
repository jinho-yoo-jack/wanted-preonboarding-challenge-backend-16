package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Getter @Setter
@SuperBuilder
public class PerformanceInfo {
    private UUID performanceId;
    private String performanceName; // 공연명
    private String performanceType;
    private Date startDate;
    private String isReserve;

    public static PerformanceInfo from(Performance entity) {
        return PerformanceInfo.builder()
            .performanceId(entity.getId())
            .performanceName(entity.getName())
            .performanceType(convertCodeToName(entity.getType()))
            .startDate(entity.getStart_date())
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
