package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.domain.Showing;
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

	public static PerformanceResponse of(Showing entity) {
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
