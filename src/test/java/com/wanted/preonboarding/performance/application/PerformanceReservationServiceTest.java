package com.wanted.preonboarding.performance.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.performance.AssertCluster;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationResponse;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("서비스: 공연 및 전시 예약 - PerformanceReservationService")
public class PerformanceReservationServiceTest extends ServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ShowingAdminService showingAdminService;

	@Test
	public void 공연을_예약_할_수_있다(){

		PerformanceRequestFactory factory = new PerformanceRequestFactory();
		PerformanceRequest showingRequest = factory.create();
		UUID performanceId = showingAdminService.register(showingRequest);

		//given
		ReservationRequest reservationRequest = new ReservationRequestFactory().create(performanceId);

		//when
		ReservationResponse reserve = reservationService.reserve(reservationRequest);

		//then
		AssertCluster.reservationAssert(showingRequest, reservationRequest, reserve);
	}

	@Test
	public void 공연을_예약_할_수_있다_fail_잘못된_id(){
		//given
		UUID wrongId = UUID.randomUUID();
		ReservationRequest reservationRequest = new ReservationRequestFactory().create(wrongId);


		//when //then
		assertThatThrownBy(()->{
			ReservationResponse reserve = reservationService.reserve(reservationRequest);
		}).isInstanceOf(EntityNotFoundException.class);

	}

	@Test
	public void 공연예약을_취소_할_수_있다(){

		PerformanceRequestFactory factory = new PerformanceRequestFactory();
		PerformanceRequest showingRequest = factory.create();
		UUID performanceId = showingAdminService.register(showingRequest);
		ReservationRequest reservationRequest = new ReservationRequestFactory().create(performanceId);

		//given
		ReservationResponse reserve = reservationService.reserve(reservationRequest);

		//when
		reservationService.cancel(reserve.id());

		//then
		assertThatThrownBy(()->{
			ReservationResponse response = reservationService.getReservation(reserve.id());
		}).isInstanceOf(EntityNotFoundException.class);
	}

}