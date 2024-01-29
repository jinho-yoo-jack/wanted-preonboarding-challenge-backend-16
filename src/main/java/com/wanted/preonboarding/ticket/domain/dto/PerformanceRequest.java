package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import java.time.LocalDate;

public record PerformanceRequest(
	String name,
	int price,
	int round,
	PerformanceType type,
	LocalDate startDate,
	boolean isReserve) {
}
