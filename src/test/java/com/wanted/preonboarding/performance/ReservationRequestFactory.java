package com.wanted.preonboarding.performance;

import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import java.util.UUID;

public class ReservationRequestFactory {

	private final PerformanceRequest performanceRequest  = new PerformanceRequestFactory().create();
	public ReservationRequest create(UUID performId) {
		return new ReservationRequest(performId, performanceRequest.name(),"예약자이름", "01012345678", 20000, 3, 'c', 3);
	}
}
