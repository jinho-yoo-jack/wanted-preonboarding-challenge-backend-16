package com.wanted.preonboarding.uitl.requestfactory;

import com.wanted.preonboarding.uitl.testdata.TestUser;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import java.util.UUID;

public class ReservationCancelRequestFactory {

	private TestUser testUser = new TestUser();
	public ReservationCancelRequest create(UUID reservationId) {
		return new ReservationCancelRequest(testUser.getName(),testUser.getPhoneNumber(),reservationId);
	}
}
