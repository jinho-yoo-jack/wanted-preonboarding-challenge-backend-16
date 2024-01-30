package com.wanted.preonboarding.performance.domain.creator;

import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.PerformanceReservation;
import com.wanted.preonboarding.performance.domain.PerformanceShowing;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;

public class PerformanceReservationCreator {

	private final PerformanceCreator performanceCreator = new PerformanceCreator();
	private final ShowingCreator showingCreator = new ShowingCreator();

	private final ReservationRequestFactory requestFactory = new ReservationRequestFactory();

	public PerformanceReservation getReservation() {
		Performance performance = performanceCreator.getPerformance();
		ReservationRequest request = requestFactory.create(performance.getId());
		PerformanceShowing showing = showingCreator.getShowing();

		return PerformanceReservation.of(
			showing,
			request,
			performance.calculateFee());
	}
}
