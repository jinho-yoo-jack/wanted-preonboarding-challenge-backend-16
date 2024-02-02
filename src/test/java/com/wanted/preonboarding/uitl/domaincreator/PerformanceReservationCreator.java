package com.wanted.preonboarding.uitl.domaincreator;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.reservation.domain.Reservation;
import com.wanted.preonboarding.reservation.domain.vo.Item;
import com.wanted.preonboarding.reservation.domain.vo.NamePhone;
import com.wanted.preonboarding.uitl.testdata.TestUser;
import java.util.UUID;

public class PerformanceReservationCreator {

	private PerformanceCreator performanceCreator = new PerformanceCreator();
	private TestUser testUser = new TestUser();

	public Reservation createReservation() {
		NamePhone namePhone = NamePhone.create(testUser.getName(), testUser.getPhoneNumber());
		return Reservation.create(namePhone);
	}

	public UUID addReserveItem(Reservation reservation){
		Performance performance = performanceCreator.getPerformance();
		Item item = new ItemCreator().create(UUID.randomUUID());
		return reservation.reserve(item,performance.calculateFee());
	}
}
