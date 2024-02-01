package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Getter @Setter
@Builder
public class ResponsePerformanceInfo {
    private UUID performanceId;
    private String performanceName; // 공연명
    private String performanceType;
    private Date startDate;
    private String isReserve;
    private int round;

    public static ResponsePerformanceInfo of (Performance entity) {
        return ResponsePerformanceInfo.builder()
                .performanceId(entity.getId())
                .performanceName(entity.getName())
                .performanceType(convertCodeToName(entity.getType()))
                .startDate(entity.getStart_date())
                .isReserve(entity.getIsReserve())
                .round(entity.getRound())
                .build();
    }

    private static String convertCodeToName(int code){
        return Arrays.stream(PerformanceType.values()).filter(value -> value.getCategory() == code)
                .findFirst()
                .orElse(PerformanceType.NONE)
                .name();
    }

}
