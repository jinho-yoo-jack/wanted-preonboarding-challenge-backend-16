package com.wanted.preonboarding.hexagonal.account.domain;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import com.wanted.preonboarding.hexagonal.account.adapter.out.web.persistence.entity.PerformanceEntity;
import com.wanted.preonboarding.performance.domain.dto.PerformanceType;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PerformanceInfo {
	 private UUID performanceId;
	    private String performanceName;
	    private String performanceType;
	    private int price;
	    private Date startDate;
	    private String isReserve;
	    
	    
	    public static PerformanceInfo of(PerformanceEntity entity) {
	        return PerformanceInfo.builder()
	            .performanceId(entity.getId())
	            .performanceName(entity.getName())
	            .performanceType(convertCodeToName(entity.getType()))
	            .price(entity.getPrice())
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

	    public boolean canReservation(){
	        return isReserve.equalsIgnoreCase("enable");
	    }
}
