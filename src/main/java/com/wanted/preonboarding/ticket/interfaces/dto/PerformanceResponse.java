package com.wanted.preonboarding.ticket.interfaces.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;

import lombok.Builder;

@Builder
public record PerformanceResponse(
	UUID id,
	String name,
	int round,
	LocalDate startDate,
	boolean reserved
) {
	public static PerformanceResponse of(PerformanceInfo performanceInfo) {
		return new PerformanceResponse(
			//convertUuidToString(performanceInfo.getPerformanceId()),
			performanceInfo.getPerformanceId(),
			performanceInfo.getPerformanceName(),
			performanceInfo.getRound(),
			performanceInfo.getStartDate(),
			performanceInfo.isReserved()
		);
	}
}
