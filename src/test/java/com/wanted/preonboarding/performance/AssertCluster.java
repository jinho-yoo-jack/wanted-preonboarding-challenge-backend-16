package com.wanted.preonboarding.performance;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceResponse;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;

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

	public static void ReservationAssert(ReservedItemResponse data, ReservationRequest request,
		PerformanceRequest performanceRequest) {
		assertThat(data.id()).isNotNull();
		assertThat(data.round()).isEqualTo(request.round());
		assertThat(data.name()).isEqualTo(performanceRequest.name());
		assertThat(data.line()).isEqualTo(request.line());
		assertThat(data.userName()).isEqualTo(request.userName());
		assertThat(data.phoneNumber()).isEqualTo(request.phoneNumber());
	}

	public static void reservationAssert(PerformanceRequest performanceRequest,
		ReservationRequest reservationRequest, ReservedItemResponse reserve) {
		assertThat(reserve.id()).isNotNull();
		assertThat(reserve.phoneNumber()).isEqualTo(reservationRequest.phoneNumber());
		assertThat(reserve.line()).isEqualTo(reservationRequest.line());
		assertThat(reserve.round()).isEqualTo(reservationRequest.round());
		assertThat(reserve.seat()).isEqualTo(reservationRequest.seat());
		assertThat(reserve.userName()).isEqualTo(reservationRequest.userName());
		assertThat(reserve.name()).isEqualTo(performanceRequest.name());
	}
}
