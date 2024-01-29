package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import java.time.LocalDate;

public record PerformanceRequest(
	String name,
	int price,
	int round,
	PerformanceType type,
	LocalDate startDate,
	boolean isReserve) {

	public static PerformanceRequest of(Performance entity) {
		return new PerformanceRequest(entity.getName(), entity.getPrice(), entity.getRound(),
			entity.getType(), entity.getStart_date(), entity.isReserve());
	}
}
