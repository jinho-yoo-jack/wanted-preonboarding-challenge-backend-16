package com.wanted.preonboarding.performance.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.domain.creator.ShowingCreator;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import org.junit.jupiter.api.Test;

public class PerformanceShowingTest {

	private final ShowingCreator showingCreator = new ShowingCreator();
	private final PerformanceShowing showing = showingCreator.getShowing();
	private final ReservationRequestFactory factory = new ReservationRequestFactory();
	private final Performance performance = showing.getPerformance();
	private final ReservationRequest reservationRequest = factory.create(performance.getId());;


	@Test
	void PerformanceShowing_reserve() {
		//given
		ReservationRequest request = reservationRequest;

		//when
		PerformanceReservation reservation = showing.reserve(request);

		//then
		assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.RESERVE);
		assertThat(reservation.getName()).isEqualTo(reservationRequest.reservationName());
		assertThat(reservation.getPhoneNumber()).isEqualTo(reservationRequest.reservationPhoneNumber());
		assertThat(reservation.getPerformanceSeatInfo().getSeat()).isEqualTo(reservationRequest.seat());
		assertThat(reservation.getPerformanceSeatInfo().getLine()).isEqualTo(reservationRequest.line());
		assertThat(reservation.getPerformanceShowing().getRound()).isEqualTo(reservationRequest.round());

	}

	@Test
	void PerformanceShowing_reserve_fail_SOLD_OUT() {
		//given
		showing.soldOut();

		//when //then
		assertThatThrownBy(()->{
			PerformanceReservation reservation = showing.reserve(reservationRequest);
		}).hasMessageContaining("매진된 상품 입니다.");
	}
}