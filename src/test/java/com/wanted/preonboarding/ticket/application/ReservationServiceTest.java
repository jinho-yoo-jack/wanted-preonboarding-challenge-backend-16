package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.ticket.AssertCluster;
import com.wanted.preonboarding.ticket.ReservationRequestFactory;
import com.wanted.preonboarding.ticket.ShowingRequestFactory;
import com.wanted.preonboarding.ticket.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.ticket.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.presentation.dto.ReservationResponse;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("에약 서비스 테스트")
public class ReservationServiceTest extends ServiceTest {

	@Autowired
	private ReservationService reservationService;

	@Autowired
	private ShowingAdminService showingAdminService;

	@Test
	public void 공연을_예약_할_수_있다(){

		ShowingRequestFactory factory = new ShowingRequestFactory();
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