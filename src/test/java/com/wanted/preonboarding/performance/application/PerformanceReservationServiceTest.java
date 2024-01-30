package com.wanted.preonboarding.performance.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.performance.AssertCluster;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("서비스: 공연 및 전시 예약 - PerformanceReservationService")
public class PerformanceReservationServiceTest extends ServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ShowingAdminService showingAdminService;

	private final PerformanceRequestFactory factory = new PerformanceRequestFactory();
	private final PerformanceRequest showingRequest = factory.create();
	private UUID performanceId ;
	private ReservationRequest reservationRequest;
	private final ReservationRequestFactory reservationRequestFactory
		= new ReservationRequestFactory();

	@BeforeEach
	void setUp() {
		performanceId = showingAdminService.register(showingRequest);
		reservationRequest = reservationRequestFactory.create(performanceId);
	}

	@Test
	public void 공연을_예약_할_수_있다() {
		//given
		ReservationRequest reservationRequest = reservationRequestFactory
			.create(performanceId);
		//when
		ReservationResponse reserve = reservationService.reserve(reservationRequest);
		//then
		AssertCluster.reservationAssert(showingRequest, reservationRequest, reserve);
	}

	@Test
	public void 공연을_예약_할_수_있다_fail_잘못된_id() {
		UUID wrongId = UUID.randomUUID();
		//given
		ReservationRequest reservationRequest = reservationRequestFactory.create(wrongId);
		//when //then
		assertThatThrownBy(() -> {
			ReservationResponse reserve = reservationService.reserve(reservationRequest);
		}).isInstanceOf(EntityNotFoundException.class);

	}

	@Test
	public void 공연예약을_취소_할_수_있다() {
		UUID performanceId = showingAdminService.register(showingRequest);
		ReservationRequest reservationRequest = reservationRequestFactory.create(
			performanceId);
		//given
		ReservationResponse reserve = reservationService.reserve(reservationRequest);
		//when
		reservationService.cancel(reserve.id());
		//then
		ReservationResponse reservation = reservationService.getReservation(reserve.userName(),
			reserve.phoneNumber()).get(0);
		assertThat(reservation.status()).isEqualTo(ReservationStatus.CANCEL);
	}

	@Test
	public void 유저의_예약목록을_조회할_수_있다() {
		//given
		ReservationResponse reserve = reservationService.reserve(reservationRequest);
		String number = reserve.phoneNumber();
		String userName = reserve.userName();
		//when
		List<ReservationResponse> reservationList = reservationService
			.getReservation(userName, number);
		//then
		ReservationResponse reservation = reservationList.get(0);
		AssertCluster.ReservationAssert(reservation,reservationRequest,showingRequest);
	}

}