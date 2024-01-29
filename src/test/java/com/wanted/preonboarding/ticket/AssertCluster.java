package com.wanted.preonboarding.ticket;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceResponse;
import com.wanted.preonboarding.ticket.domain.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.ReservationResponse;

public class AssertCluster {
	public static void performanceAssert(
		PerformanceRequest request, PerformanceResponse response) {
		assertThat(response.isReserve()).isEqualTo(request.isReserve());
		assertThat(response.startDate()).isEqualTo(request.startDate());
		assertThat(response.performanceId()).isNotNull();
		assertThat(response.performanceName()).isEqualTo(request.name());
		assertThat(response.performanceType()).isEqualTo(request.type().toString());
		assertThat(response.round()).isEqualTo(request.round());
	}

	public static void ReservationAssert(ReservationResponse data, ReservationRequest request,
		PerformanceRequest performanceRequest) {
		assertThat(data.id()).isNotNull();
		assertThat(data.round()).isEqualTo(request.round());
		assertThat(data.name()).isEqualTo(performanceRequest.name());
		assertThat(data.line()).isEqualTo(request.line());
		assertThat(data.userName()).isEqualTo(request.reservationName());
		assertThat(data.phoneNumber()).isEqualTo(request.reservationPhoneNumber());
	}

	public static void reservationAssert(PerformanceRequest showingRequest,
		ReservationRequest reservationRequest, ReservationResponse reserve) {
		assertThat(reserve.id()).isNotNull();
		assertThat(reserve.phoneNumber()).isEqualTo(reservationRequest.reservationPhoneNumber());
		assertThat(reserve.line()).isEqualTo(reservationRequest.line());
		assertThat(reserve.round()).isEqualTo(reservationRequest.round());
		assertThat(reserve.seat()).isEqualTo(reservationRequest.seat());
		assertThat(reserve.userName()).isEqualTo(reservationRequest.reservationName());
		assertThat(reserve.name()).isEqualTo(showingRequest.name());
	}
}
