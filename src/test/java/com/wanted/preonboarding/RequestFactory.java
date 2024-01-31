package com.wanted.preonboarding;

import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationCancelRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import java.util.UUID;

public class RequestFactory {

	private TestUser testUser;

	public static ReservationCancelRequest getCancelRequest(UUID reserveItemNo) {
		return new ReservationCancelRequestFactory().create(reserveItemNo);
	}

	public static PerformRequest getPerformRegister() {
		return new PerformanceRequestFactory().create();
	}

	public static ReservationRequest getReservation(UUID performId) {
		return new ReservationRequestFactory().create(performId);
	}
}
