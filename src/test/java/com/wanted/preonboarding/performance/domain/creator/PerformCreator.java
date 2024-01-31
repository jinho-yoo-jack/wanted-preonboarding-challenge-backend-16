package com.wanted.preonboarding.performance.domain.creator;

import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceRequest;

public class PerformCreator {

	private final Performance performance = new PerformanceCreator().getPerformance();
	private final ShowRoomCreator showRoomCreator = new ShowRoomCreator();

	private final PerformanceRequestFactory performanceRequestFactory = new PerformanceRequestFactory();

	public Perform getPerform() {
		PerformanceRequest performanceRequest = performanceRequestFactory.create();

		return Perform.create(
			performance,
			showRoomCreator.getShowRoom(),
			performanceRequest.round(),
			performanceRequest.startDate(),
			performanceRequest.isReserve()
			);
	}

}
