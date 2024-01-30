package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.performance.AssertCluster;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationResponse;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("에약 서비스 테스트")
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


}