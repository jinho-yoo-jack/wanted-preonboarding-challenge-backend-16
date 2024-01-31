package com.wanted.preonboarding.performance;

import com.wanted.preonboarding.TestUser;
import com.wanted.preonboarding.reservation.domain.vo.Item;
import com.wanted.preonboarding.reservation.domain.vo.SeatInfo;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import java.util.UUID;

public class ItemCreator {

	private TestUser testUser = new TestUser();
	private TestPerformance testPerformance = new TestPerformance();
	private TestSeatInfo testSeatInfo = new TestSeatInfo();

	public Item create(UUID performId) {
		SeatInfo seatInfo = SeatInfo.create(testSeatInfo.getLine(), testSeatInfo.getSeat());
		return Item.create(
			performId,
			testUser.getName(),
			testPerformance.getRound(),
			seatInfo);
	}
}
