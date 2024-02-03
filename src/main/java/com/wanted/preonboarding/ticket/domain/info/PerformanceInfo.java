package com.wanted.preonboarding.ticket.domain.info;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

@Data
@Builder
public class PerformanceInfo {
	private UUID performanceId;
	private String performanceName;
	private String performanceType;
	private LocalDate startDate;
	private boolean reserved;
	private int round;

	public static PerformanceInfo of(Performance entity) {
		return PerformanceInfo.builder()
			.performanceId(entity.getPerformanceId())
			.performanceName(entity.getPeformanceName())
			.performanceType(convertCodeToName(entity.getType()))
			.startDate(entity.getStart_date())
			.reserved(entity.isReserved())
			.round(entity.getRound())
			.build();
	}

	private static String convertCodeToName(int code) {
		return Arrays.stream(PerformanceType.values()).filter(value -> value.getCategory() == code)
			.findFirst()
			.orElse(PerformanceType.NONE)
			.name();
	}

}
