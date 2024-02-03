package com.wanted.preonboarding.ticket.interfaces.dto;

import java.time.LocalDate;

import com.wanted.preonboarding.ticket.domain.info.PerformanceType;

public record PerformanceAvailabilityRequest(
	String name,
	int price,
	int round,
	PerformanceType type,
	LocalDate startDate,
	boolean reserved
) {

}
