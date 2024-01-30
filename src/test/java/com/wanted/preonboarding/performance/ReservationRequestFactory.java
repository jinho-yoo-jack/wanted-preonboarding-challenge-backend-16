package com.wanted.preonboarding.performance;

import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import java.util.UUID;

public class ReservationRequestFactory {

	public ReservationRequest create(UUID showingId) {
		return new ReservationRequest(showingId, "예약자이름", "01012345678", 20000, 3, 'c', 3);
	}
}
