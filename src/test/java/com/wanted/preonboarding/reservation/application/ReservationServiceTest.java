package com.wanted.preonboarding.reservation.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.core.exception.ReservationSoldOutException;
import com.wanted.preonboarding.performance.AssertCluster;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationCancelRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.application.PerformAdminService;
import com.wanted.preonboarding.performance.application.PerformService;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformRequest;
import com.wanted.preonboarding.reservation.domain.vo.ReservationStatus;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
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



	private final PerformanceRequestFactory factory = new PerformanceRequestFactory();
	private final PerformRequest performRequest = factory.create();
	private UUID performID;
	private ReservationRequest reservationRequest;
	private final ReservationRequestFactory reservationRequestFactory
		= new ReservationRequestFactory();

	@BeforeEach
	void setUp() {
		performID = performAdminService.register(performRequest);
		reservationRequest = reservationRequestFactory.create(performID);
	}

	@Test
	public void 공연을_예약_할_수_있다() {
		//given
		ReservationRequest reservationRequest = reservationRequestFactory
			.create(performID);
		//when
		ReservedItemResponse reserve = reservationService.reserve(reservationRequest);
		//then
		AssertCluster.reservationAssert(performRequest, reservationRequest, reserve);
	}

	@Test
	public void 공연을_예약_할_수_있다_fail_잘못된_id() {
		UUID wrongId = UUID.randomUUID();
		//given
		ReservationRequest reservationRequest = reservationRequestFactory.create(wrongId);
		//when //then
		assertThatThrownBy(() -> {
			ReservedItemResponse reserve = reservationService.reserve(reservationRequest);
		}).isInstanceOf(EntityNotFoundException.class);

	}

	@Test
	public void 공연을_예약_할_수_있다_fail_SOLD_OUT() {
		//given
		ReservationRequest reservationRequest = reservationRequestFactory
			.create(performID);

		//when
		performService.soldOut(performID);
		//then
		assertThatThrownBy(() -> {
				ReservedItemResponse reserve = reservationService.reserve(reservationRequest);
			}).isInstanceOf(ReservationSoldOutException.class).hasMessageContaining("매진된 상품 입니다.");

	}

	@Test
	public void 공연예약을_취소_할_수_있다() {
		//given
		ReservationRequest reservationRequest = reservationRequestFactory
			.create(performID);
		ReservedItemResponse reserve = reservationService.reserve(reservationRequest);

		//when
		ReservationCancelRequest cancelRequest
			= new ReservationCancelRequestFactory().create(reserve.id());
		reservationService.cancel(cancelRequest);
		//then
		ReservedItemResponse reservation = reservationService.getReservations(reserve.userName(),
			reserve.phoneNumber()).get(0);
		assertThat(reservation.status()).isEqualTo(ReservationStatus.CANCEL);
	}

	@Test
	public void 유저의_예약목록을_조회할_수_있다() {
		//given
		ReservedItemResponse reserve = reservationService.reserve(reservationRequest);
		String number = reserve.phoneNumber();
		String userName = reserve.userName();
		//when
		List<ReservedItemResponse> reservationList = reservationService
			.getReservations(userName, number);
		//then
		ReservedItemResponse reservation = reservationList.get(0);
		AssertCluster.ReservationAssert(reservation,reservationRequest, performRequest);
	}

}