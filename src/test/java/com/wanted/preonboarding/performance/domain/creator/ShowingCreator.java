package com.wanted.preonboarding.performance.domain.creator;

import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.PerformanceShowing;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;

public class ShowingCreator {

	private final Performance performance = new PerformanceCreator().getPerformance();
	private final ShowRoomCreator showRoomCreator = new ShowRoomCreator();

	private final PerformanceRequestFactory performanceRequestFactory = new PerformanceRequestFactory();

	public PerformanceShowing getShowing() {
		PerformanceRequest performanceRequest = performanceRequestFactory.create();

		return PerformanceShowing.create(
			performance,
			showRoomCreator.getShowRoom(),
			performanceRequest.round(),
			performanceRequest.startDate(),
			performanceRequest.isReserve()
			);
	}

}
