package com.wanted.preonboarding.performance;

import com.wanted.preonboarding.TestUser;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import java.util.UUID;

public class ReservationCancelRequestFactory {

	private TestUser testUser = new TestUser();
	public ReservationCancelRequest create(UUID reservationId) {
		return new ReservationCancelRequest(testUser.getName(),testUser.getPhoneNumber(),reservationId);
	}
}
