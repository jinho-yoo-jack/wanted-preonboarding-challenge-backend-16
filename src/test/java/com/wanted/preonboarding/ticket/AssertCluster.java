package com.wanted.preonboarding.ticket;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceResponse;

public class AssertCluster {
	public static void performanceAssert(
		PerformanceRequest request, PerformanceResponse response) {
		assertThat(response.isReserve()).isEqualTo(request.isReserve());
		assertThat(response.startDate()).isEqualTo(request.startDate());
		assertThat(response.performanceId()).isNotNull();
		assertThat(response.performanceName()).isEqualTo(request.name());
		assertThat(response.performanceType()).isEqualTo(request.type().toString());
		assertThat(response.round()).isEqualTo(request.round());
	}
}
