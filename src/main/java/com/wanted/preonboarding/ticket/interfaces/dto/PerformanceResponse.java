package com.wanted.preonboarding.ticket.interfaces.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceDTO;

import lombok.Builder;

@Builder
public record PerformanceResponse(
	UUID id,
	String name,
	int round,
	LocalDate startDate,
	boolean reserved
) {
	public static PerformanceResponse of(PerformanceDTO performanceDTO) {
		return new PerformanceResponse(
			performanceDTO.getPerformanceId(),
			performanceDTO.getPerformanceName(),
			performanceDTO.getRound(),
			performanceDTO.getStartDate(),
			performanceDTO.isReserved()
		);
	}
}
