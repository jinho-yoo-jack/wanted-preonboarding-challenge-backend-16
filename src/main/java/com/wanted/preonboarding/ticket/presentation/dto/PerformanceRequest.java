package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.domain.vo.PerformanceType;
import java.time.LocalDate;

public record PerformanceRequest(
	String name,
	int price,
	int round,
	PerformanceType type,
	LocalDate startDate,
	boolean isReserve) {
}
