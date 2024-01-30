package com.wanted.preonboarding.performance.domain.event;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.application.PerformanceCancelEventService;
import com.wanted.preonboarding.performance.application.ReservationService;
import com.wanted.preonboarding.performance.application.ShowingAdminService;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationResponse;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@DisplayName("이벤트: 예약 취소")
public class EventPublisherTest extends ServiceTest {

	@Autowired
	private EventPublisher eventPublisher;

	@Autowired
	private PerformanceCancelEventService performanceCancelEventService;

	@Autowired
	private ShowingAdminService showingAdminService;

	@Autowired
	private ReservationService reservationService;

	@Test
	public void 이벤트_발행(){
		PerformanceRequestFactory performanceRequestFactory = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = performanceRequestFactory.create();
		UUID showingId = showingAdminService.register(performanceRequest);

		ReservationRequestFactory factory = new ReservationRequestFactory();
		ReservationRequest request = factory.create(showingId);
		ReservationResponse reserve = reservationService.reserve(request);

		UUID userId = UUID.randomUUID();
		performanceCancelEventService.subscribe(showingId,userId);

		reservationService.cancel(reserve.id());
//		ReservationCancelEvent event = new ReservationCancelEvent(showingId,reserve.id());
//		eventPublisher.cancelEventPublish(event);
	}

}