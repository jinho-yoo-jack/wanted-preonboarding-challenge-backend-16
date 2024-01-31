package com.wanted.preonboarding.performance.framwork.presentation.dto;

import com.wanted.preonboarding.performance.domain.vo.PerformanceType;
import java.time.LocalDate;

public record PerformanceRequest(
	String name,
	int price,
	int round,
	PerformanceType type,
	LocalDate startDate,
	boolean isReserve) {
}
