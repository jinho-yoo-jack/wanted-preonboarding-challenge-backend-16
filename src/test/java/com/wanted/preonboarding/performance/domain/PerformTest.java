package com.wanted.preonboarding.performance.domain;

import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.domain.creator.PerformCreator;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import org.junit.jupiter.api.DisplayName;

@DisplayName("도메인: 공연 및 전시 상영 - Perform")
public class PerformTest {

	private final PerformCreator performCreator = new PerformCreator();
	private final Perform perform = performCreator.getPerform();
	private final ReservationRequestFactory factory = new ReservationRequestFactory();
	private final Performance performance = perform.getPerformance();
	private final ReservationRequest reservationRequest = factory.create(performance.getId());;


}