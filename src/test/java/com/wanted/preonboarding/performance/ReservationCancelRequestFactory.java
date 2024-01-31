package com.wanted.preonboarding.performance;

import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import java.util.UUID;

public class ReservationCancelRequestFactory {

	public ReservationCancelRequest create(ReservationRequest request, UUID reserveItemBO) {
		return new ReservationCancelRequest(request.userName(),request.phoneNumber(),reserveItemBO);
	}
}
