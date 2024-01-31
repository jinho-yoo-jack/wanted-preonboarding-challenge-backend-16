package com.wanted.preonboarding.reservation.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.performance.ItemCreator;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.domain.creator.PerformanceReservationCreator;
import com.wanted.preonboarding.reservation.domain.Reservation;
import com.wanted.preonboarding.reservation.domain.vo.Item;
import com.wanted.preonboarding.reservation.domain.vo.ReservationStatus;
import com.wanted.preonboarding.reservation.domain.vo.ReserveItem;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@DisplayName("도메인: 공연 및 전시 예약 - Reservation")
public class ReservationTest {

	@Test
	void reservation_cancel() {
		//given
		PerformanceReservationCreator creator = new PerformanceReservationCreator();
		Reservation reservation = creator.getReservation();
		UUID reserveItemNo = creator.addReserve(reservation);

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
		UUID performId = UUID.randomUUID();
		ReservationRequest reservationRequest = new ReservationRequestFactory().create(performId);
		PerformanceReservationCreator creator = new PerformanceReservationCreator();
		Reservation reservation = creator.getReservation();
		Item item = new ItemCreator().create(reservationRequest);
		//when
		UUID reserveItemId = reservation.reserve(item,300);

		//then
		ReserveItem reserveItem = reservation.getReserveItem(reserveItemId);
		assertThat(reserveItem.getReservationStatus()).isEqualTo(ReservationStatus.RESERVE);
		assertThat(reservation.getNamePhone().getName()).isEqualTo(reservationRequest.userName());
		assertThat(reservation.getNamePhone().getPhoneNumber()).isEqualTo(reservationRequest.phoneNumber());
		assertThat(reserveItem.getItem().getSeatInfo().getSeat()).isEqualTo(reservationRequest.seat());
		assertThat(reserveItem.getItem().getSeatInfo().getLine()).isEqualTo(reservationRequest.line());
		assertThat(reserveItem.getItem().getRound()).isEqualTo(reservationRequest.round());

	}



}