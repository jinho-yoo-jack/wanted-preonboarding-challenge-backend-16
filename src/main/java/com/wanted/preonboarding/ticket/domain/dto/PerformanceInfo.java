package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class PerformanceInfo {
    private UUID performanceId; // 공연 정보
    private String performanceName; // 공연 이름
    private String performanceType; // 공연 타입
    private Date startDate; // 공연 시작 일
    private String isReserve; // 예약 여부

    public static PerformanceInfo from(Performance entity) {
        return PerformanceInfo.builder()
            .performanceId(entity.getId())
            .performanceName(entity.getName())
            .performanceType(convertCodeToName(entity.getType()))
            .startDate(entity.getStartDate())
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
