package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class ResponsePerformanceInfo {

    private String performanceName; // 공연명
    private String performanceType;
    private Date startDate;
    private String isReserve;
    private int round;

    public static ResponsePerformanceInfo of (Performance entity) {
        return ResponsePerformanceInfo.builder()
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
