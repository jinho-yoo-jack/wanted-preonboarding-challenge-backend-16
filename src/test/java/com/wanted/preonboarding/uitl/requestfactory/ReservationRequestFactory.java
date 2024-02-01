package com.wanted.preonboarding.uitl.requestfactory;

import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.uitl.testdata.TestPerformance;
import com.wanted.preonboarding.uitl.testdata.TestSeatInfo;
import com.wanted.preonboarding.uitl.testdata.TestUser;
import java.util.UUID;

public class ReservationRequestFactory {
	private TestUser testUser = new TestUser();
	private TestSeatInfo info = new TestSeatInfo();
	private TestPerformance testPerformance = new TestPerformance();


	private final PerformRequest performanceRequest  = new PerformanceRequestFactory().create();
	public ReservationRequest create(UUID performId) {
		return new ReservationRequest(performId,
			performanceRequest.name(),
			testUser.getName(),
			testUser.getPhoneNumber(),
			testPerformance.getPrice(),
			info.getLine(),
			info.getSeat());
	}
}
