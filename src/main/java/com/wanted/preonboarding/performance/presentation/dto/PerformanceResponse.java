package com.wanted.preonboarding.performance.presentation.dto;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.Perform;
import java.time.LocalDate;
import java.util.UUID;

public record PerformanceResponse(
	UUID performanceId,
	String performanceName,
	String performanceType,
	LocalDate startDate,
	boolean isReserve,
	int round

) {

	public static PerformanceResponse of(Perform entity) {
		Performance performance = entity.getPerformance();
		return new PerformanceResponse(
			entity.getId(),
			performance.getName(),
			performance.getType().toString(),
			entity.getStartDate(),
			entity.isReservationAvailable(),
			entity.getRound()
		);
	}

}
