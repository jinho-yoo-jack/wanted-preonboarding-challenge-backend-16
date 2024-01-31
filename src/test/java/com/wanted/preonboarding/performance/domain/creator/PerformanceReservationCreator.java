package com.wanted.preonboarding.performance.domain.creator;

import com.wanted.preonboarding.performance.ItemCreator;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.reservation.domain.Reservation;
import com.wanted.preonboarding.reservation.domain.vo.NamePhone;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import java.util.UUID;

public class PerformanceReservationCreator {

	private final PerformanceCreator performanceCreator = new PerformanceCreator();
	private final PerformCreator performCreator = new PerformCreator();

	private final ReservationRequestFactory requestFactory = new ReservationRequestFactory();

	public Reservation getReservation() {
		Performance performance = performanceCreator.getPerformance();
		ReservationRequest request = requestFactory.create(performance.getId());

		Reservation reservation = Reservation.create(
			NamePhone.create(request.userName(), request.phoneNumber()));


		return reservation;
	}

	public UUID addReserve(Reservation reservation){
		Performance performance = performanceCreator.getPerformance();
		ReservationRequest request = requestFactory.create(performance.getId());
		return reservation.reserve(new ItemCreator().create(request),request.amount());
	}
}
