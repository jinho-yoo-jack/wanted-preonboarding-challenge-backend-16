package com.wanted.preonboarding.ticket;

import com.wanted.preonboarding.ticket.domain.dto.ReservationRequest;
import java.util.UUID;

public class ReservationRequestFactory {

	public ReservationRequest create(UUID performanceId) {
		return new ReservationRequest(performanceId, "예약자이름", "01012345678", 20000, 3, 'c', 3);
	}
}
