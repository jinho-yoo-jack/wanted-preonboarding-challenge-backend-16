package com.wanted.preonboarding.performance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.performance.domain.creator.PerformanceReservationCreator;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import com.wanted.preonboarding.performance.presentation.PerformanceReservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@DisplayName("도메인: 공연 및 전시 예약 - PerformanceReservation")
public class PerformanceReservationTest {

	@Test
	void reservation_cancel() {
		//given
		PerformanceReservationCreator creator = new PerformanceReservationCreator();
		PerformanceReservation reservation = creator.getReservation();

		//when
		int refundAmount = reservation.cancel();

		//then
		assertThat(reservation.getReservationStatus()).isEqualTo(ReservationStatus.CANCEL);
		assertThat(refundAmount).isEqualTo(reservation.getFee());
	}
}