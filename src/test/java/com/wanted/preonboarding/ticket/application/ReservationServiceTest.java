package com.wanted.preonboarding.ticket.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.ticket.PerformanceRequestFactory;
import com.wanted.preonboarding.ticket.ReservationRequestFactory;
import com.wanted.preonboarding.ticket.domain.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.ReservationResponse;
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

		PerformanceRequestFactory factory = new PerformanceRequestFactory();
		UUID performanceId = showingAdminService.register(factory.create());

		//given
		ReservationRequest reservationRequest = new ReservationRequestFactory().create(performanceId);

		//when
		ReservationResponse reserve = reservationService.reserve(reservationRequest);

		//then
		assertThat(reserve.id()).isNotNull();
	}
}