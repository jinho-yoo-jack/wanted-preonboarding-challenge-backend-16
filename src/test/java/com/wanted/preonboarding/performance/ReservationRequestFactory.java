package com.wanted.preonboarding.performance;

import com.wanted.preonboarding.TestUser;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
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
			testPerformance.getRound(),
			info.getLine(),
			info.getSeat());
	}
}
