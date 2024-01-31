package com.wanted.preonboarding.uitl;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.uitl.testdata.TestUser;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceResponse;
import com.wanted.preonboarding.uitl.testdata.TestPerformance;
import com.wanted.preonboarding.uitl.testdata.TestSeatInfo;
import com.wanted.preonboarding.reservation.domain.Reservation;
import com.wanted.preonboarding.reservation.domain.vo.ReservationStatus;
import com.wanted.preonboarding.reservation.domain.vo.ReserveItem;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;
import java.util.UUID;

public class AssertCluster {

	private static TestUser testUser = new TestUser();
	private static TestPerformance testPerformance = new TestPerformance();
	private static TestSeatInfo testSeatInfo = new TestSeatInfo();

	public static void performanceAssert(
		PerformRequest request, PerformanceResponse response) {
		assertThat(response.isReserve()).isEqualTo(request.isReserve());
		assertThat(response.startDate()).isEqualTo(request.startDate());
		assertThat(response.performanceId()).isNotNull();
		assertThat(response.performanceName()).isEqualTo(request.name());
		assertThat(response.performanceType()).isEqualTo(request.type().toString());
		assertThat(response.round()).isEqualTo(request.round());
	}

	public static void ReservationAssert(ReservedItemResponse data, ReservationRequest request,
		PerformRequest performanceRequest) {
		assertThat(data.id()).isNotNull();
		assertThat(data.round()).isEqualTo(request.round());
		assertThat(data.name()).isEqualTo(performanceRequest.name());
		assertThat(data.line()).isEqualTo(request.line());
		assertThat(data.userName()).isEqualTo(request.userName());
		assertThat(data.phoneNumber()).isEqualTo(request.phoneNumber());
	}

	public static void reservationAssert(PerformRequest performanceRequest,
		ReservationRequest reservationRequest, ReservedItemResponse reserve) {
		assertThat(reserve.id()).isNotNull();
		assertThat(reserve.phoneNumber()).isEqualTo(reservationRequest.phoneNumber());
		assertThat(reserve.line()).isEqualTo(reservationRequest.line());
		assertThat(reserve.round()).isEqualTo(reservationRequest.round());
		assertThat(reserve.seat()).isEqualTo(reservationRequest.seat());
		assertThat(reserve.userName()).isEqualTo(reservationRequest.userName());
		assertThat(reserve.name()).isEqualTo(performanceRequest.name());
	}

	public static void reserveItem(Reservation reservation, UUID reserveItemId) {
		ReserveItem reserveItem = reservation.getReserveItem(reserveItemId);

		assertThat(reserveItem.getReservationStatus()).isEqualTo(ReservationStatus.RESERVE);
		assertThat(reservation.getNamePhone().getName()).isEqualTo(testUser.getName());
		assertThat(reservation.getNamePhone().getPhoneNumber()).isEqualTo(testUser.getPhoneNumber());
		assertThat(reserveItem.getItem().getSeatInfo().getSeat()).isEqualTo(testSeatInfo.getSeat());
		assertThat(reserveItem.getItem().getSeatInfo().getLine()).isEqualTo(testSeatInfo.getLine());
		assertThat(reserveItem.getItem().getRound()).isEqualTo(testPerformance.getRound());
	}
}
