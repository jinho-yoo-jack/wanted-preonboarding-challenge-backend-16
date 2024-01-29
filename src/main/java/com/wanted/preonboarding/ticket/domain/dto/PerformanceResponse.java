package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
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

	public static PerformanceResponse of(Performance entity) {
		return new PerformanceResponse(
			entity.getId(),
			entity.getName(),
			entity.getType().toString(),
			entity.getStart_date(),
			entity.isReserve(),
			entity.getRound()
		);
	}

}
