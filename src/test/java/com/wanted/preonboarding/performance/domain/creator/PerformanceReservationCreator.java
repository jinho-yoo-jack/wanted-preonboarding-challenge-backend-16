package com.wanted.preonboarding.performance.domain.creator;

import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.presentation.PerformanceReservation;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;

public class PerformanceReservationCreator {

	private final PerformanceCreator performanceCreator = new PerformanceCreator();
	private final ShowingCreator showingCreator = new ShowingCreator();

	private final ReservationRequestFactory requestFactory = new ReservationRequestFactory();

	public PerformanceReservation getReservation() {
		Performance performance = performanceCreator.getPerformance();
		ReservationRequest request = requestFactory.create(performance.getId());
		Perform showing = showingCreator.getShowing();

		return PerformanceReservation.of(
			showing,
			request,
			performance.calculateFee());
	}
}
