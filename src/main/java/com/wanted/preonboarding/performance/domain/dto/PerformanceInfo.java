package com.wanted.preonboarding.performance.domain.dto;

import java.util.Arrays;
import java.util.Date;

import com.wanted.preonboarding.performance.domain.entity.Performance;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * 
 * 
 * 
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceInfo {
	
    private String performanceName;
    private String performanceType;
    private Date startDate;
    private String isReserve;
    
    /**
     * 예약 가능한 공연 및 전시 정보
     * @param entity
     * @return
     */
    public static PerformanceInfo enablePerformance(Performance entity) {
        return PerformanceInfo.builder()
            .performanceName(entity.getName())
            .performanceType(convertCodeToName(entity.getType()))
            .startDate(entity.getStart_date())
            .isReserve(entity.getIsReserve())
            .build();
    }

    /**
     * 공연 타입 맵핑
     * @param code
     * @return
     */
    private static String convertCodeToName(int code){
        return Arrays.stream(PerformanceType.values()).filter(value -> value.getCategory() == code)
            .findFirst()
            .orElse(PerformanceType.NONE)
            .name();
    }
}
