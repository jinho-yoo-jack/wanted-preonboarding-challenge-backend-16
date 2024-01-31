package com.wanted.preonboarding.uitl.domaincreator;

import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import com.wanted.preonboarding.uitl.requestfactory.PerformanceRequestFactory;

public class PerformCreator {

	private final Performance performance = new PerformanceCreator().getPerformance();
	private final ShowRoomCreator showRoomCreator = new ShowRoomCreator();

	private final PerformanceRequestFactory performanceRequestFactory = new PerformanceRequestFactory();

	public Perform getPerform() {
		PerformRequest performanceRequest = performanceRequestFactory.create();

		return Perform.create(
			performance,
			showRoomCreator.getShowRoom(),
			performanceRequest.round(),
			performanceRequest.startDate(),
			performanceRequest.isReserve()
			);
	}

}
