package com.wanted.preonboarding.uitl.requestfactory;

import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import com.wanted.preonboarding.uitl.testdata.TestPerformance;

public class PerformanceRequestFactory {

	private TestPerformance testPerformance = new TestPerformance();

	public PerformRequest create() {
		return new PerformRequest(
			testPerformance.getName(),
			testPerformance.getPrice(),
			testPerformance.getRound(),
			testPerformance.getType(),
			testPerformance.getStart_Date(),
			testPerformance.isReserve());
	}

	public void setReserve(boolean b) {
		testPerformance.setReserve(b);
	}
}
