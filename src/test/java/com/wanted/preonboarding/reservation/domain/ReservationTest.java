package com.wanted.preonboarding.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.reservation.domain.vo.Item;
import com.wanted.preonboarding.reservation.domain.vo.ReservationStatus;
import com.wanted.preonboarding.reservation.domain.vo.ReserveItem;
import com.wanted.preonboarding.uitl.AssertCluster;
import com.wanted.preonboarding.uitl.domaincreator.ItemCreator;
import com.wanted.preonboarding.uitl.domaincreator.PerformanceCreator;
import com.wanted.preonboarding.uitl.domaincreator.PerformanceReservationCreator;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("도메인: 공연 및 전시 예약 - Reservation")
public class ReservationTest {

	private PerformanceReservationCreator reservationCreator;

	@BeforeEach
	void setUp() {
		reservationCreator = new PerformanceReservationCreator();
	}

	@Test
	void reservation_cancel() {
		//given
		Reservation reservation = reservationCreator.createReservation();
		UUID reserveItemNo = reservationCreator.addReserveItem(reservation);
		//when
		int refundAmount = reservation.cancel(reserveItemNo);
		//then
		ReserveItem reserveItem = reservation.getReserveItem(reserveItemNo);
		assertThat(reserveItem.getReservationStatus()).isEqualTo(ReservationStatus.CANCEL);
		assertThat(refundAmount).isEqualTo(reserveItem.getFee());
	}

	@Test
	void reservation_reserve() {
		//given
		Performance performance = new PerformanceCreator().getPerformance();
		Item item = new ItemCreator().create( UUID.randomUUID());
		//when
		Reservation reservation = reservationCreator.createReservation();
		UUID reserveItemId = reservation.reserve(item, performance.calculateFee());
		//then
		AssertCluster.reserveItem(reservation,reserveItemId);

	}


}