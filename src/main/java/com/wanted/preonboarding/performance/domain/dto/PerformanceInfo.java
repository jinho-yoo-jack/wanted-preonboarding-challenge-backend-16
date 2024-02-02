package com.wanted.preonboarding.performance.domain.dto;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.domain.entity.PerformanceSeatInfo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
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
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class PerformanceInfo {
	
	@NotNull(message = "은 비어있을수 없습니다.")
	private UUID performanceId;
    private String performanceName;
    private String performanceType;
    private Date startDate;
    private String isReserve;
    
    private int price;
    private int round;
    private int gate;
    private int seat;
    private String line;
    
    
    /**
     * 예약 가능한 공연 및 전시 정보
     * @param entity
     * @return
     */
    public static PerformanceInfo enablePerformance(Performance entity) {
        return PerformanceInfo.builder()
        	.performanceId(entity.getPerformanceId())
            .performanceName(entity.getName())
            .performanceType(convertCodeToName(entity.getType()))
            .startDate(entity.getStart_date())
            .isReserve(entity.getIsReserve())
            .price(entity.getPrice())
            .round(entity.getRound())
            .build();
    }
    
    /**
     * 예약 가능한 공연 및 전시 정보
     * @param entity
     * @return
     */
    public static PerformanceInfo detailPerformanceList(PerformanceSeatInfo entity) {
        return PerformanceInfo.builder()
        	.performanceId(entity.getPerformance().getPerformanceId())
            .performanceName(entity.getPerformance().getName())
            .performanceType(convertCodeToName(entity.getPerformance().getType()))
            .startDate(entity.getPerformance().getStart_date())
            .price(entity.getPerformance().getPrice())
            .round(entity.getRound())
            .gate(entity.getGate())
            .seat(entity.getSeat())
            .line(entity.getLine())
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
