package com.wanted.preonboarding.reservation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.preonboarding.uitl.requestfactory.RequestFactory;
import com.wanted.preonboarding.uitl.basetest.ServiceTest;
import com.wanted.preonboarding.core.exception.ReservationSoldOutException;
import com.wanted.preonboarding.uitl.AssertCluster;
import com.wanted.preonboarding.performance.application.PerformAdminService;
import com.wanted.preonboarding.performance.application.PerformService;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import com.wanted.preonboarding.reservation.domain.vo.ReservationStatus;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("서비스: 공연 및 전시 예약 - PerformanceReservationService")
public class ReservationServiceTest extends ServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private PerformAdminService performAdminService;

	@Autowired
	private PerformService performService;


	private PerformRequest performRequest;
	private ReservationRequest reservationRequest;
	private UUID performID;

	@BeforeEach
	void setUp() {
		//performRequest
		performRequest = RequestFactory.getPerformRegister();
		performID = performAdminService.register(performRequest);
		reservationRequest = RequestFactory.getReservation(performID);

	}

	@Test
	public void 공연을_예약_할_수_있다() {
		//given
		performID = performAdminService.register(performRequest);
		reservationRequest = RequestFactory.getReservation(performID);
		//when
		ReservedItemResponse reserve = reservationService.reserve(reservationRequest);
		//then
		AssertCluster.reservationAssert(performRequest, reservationRequest, reserve);
	}

	@Test
	public void 공연을_예약_할_수_있다_fail_잘못된_id() {
		//given
		UUID wrongId = UUID.randomUUID();
		ReservationRequest reservationRequest = RequestFactory.getReservation(wrongId);
		//when //then
		assertThatThrownBy(() -> {
			reservationService.reserve(reservationRequest);
		}).isInstanceOf(EntityNotFoundException.class);
	}

	@Test
	public void 공연을_예약_할_수_있다_fail_SOLD_OUT() {
		//given
		performID = performAdminService.register(performRequest);
		reservationRequest = RequestFactory.getReservation(performID);
		//when
		performService.soldOut(performID);
		//then
		assertThatThrownBy(() -> {
			reservationService.reserve(reservationRequest);
		}).isInstanceOf(ReservationSoldOutException.class).hasMessageContaining("매진된 상품 입니다.");

	}

	@Test
	public void 공연예약을_취소_할_수_있다() {
		//given
		ReservedItemResponse reserve = reservationService.reserve(reservationRequest);
		String number = reserve.phoneNumber();
		String userName = reserve.userName();
		ReservationCancelRequest cancelRequest = RequestFactory.getCancel(reserve.id());
		//when
		reservationService.cancel(cancelRequest);
		//then
		final var reservationList = reservationService.getReservations(userName, number);
		assertThat(reservationList.get(0).status()).isEqualTo(ReservationStatus.CANCEL);
	}

	@Test
	public void 유저의_예약목록을_조회할_수_있다() {
		//given
		ReservedItemResponse reserve = reservationService.reserve(reservationRequest);
		String number = reserve.phoneNumber();
		String userName = reserve.userName();
		//when
		final var reservationList = reservationService.getReservations(userName, number);
		//then
		ReservedItemResponse reservation = reservationList.get(0);
		AssertCluster.ReservationAssert(reservation, reservationRequest, performRequest);
	}

}