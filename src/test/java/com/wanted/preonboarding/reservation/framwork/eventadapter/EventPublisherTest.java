package com.wanted.preonboarding.reservation.framwork.eventadapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationCancelRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.application.PerformAdminService;
import com.wanted.preonboarding.performance.application.PerformCancelEventService;
import com.wanted.preonboarding.performance.framwork.infrastructure.eventAdapter.ReservationCancelEventListener;
import com.wanted.preonboarding.performance.framwork.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.reservation.application.ReservationService;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@DisplayName("이벤트: 예약 취소 - 발행")
public class EventPublisherTest extends ServiceTest {

	@Autowired
	private ReservationCancelEventListener reservationCancelEventListener;

	@Autowired
	private PerformAdminService showingAdminService;

	@Autowired
	private ReservationService reservationService;

	@MockBean
	private PerformCancelEventService performanceCancelEventService;

	@Autowired
	@Qualifier("customThreadPoolTaskExecutor")
	private Executor customThreadPoolTaskExecutor;

	@Test
	public void 이벤트_발행() throws InterruptedException {
		PerformanceRequestFactory performanceRequestFactory = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = performanceRequestFactory.create();
		UUID performId = showingAdminService.register(performanceRequest);
		ReservationRequestFactory factory = new ReservationRequestFactory();
		ReservationRequest request = factory.create(performId);
		ReservedItemResponse reserve = reservationService.reserve(request);
		UUID userId = UUID.randomUUID();

		//given
		reservationCancelEventListener.subscribe(performId, userId);
		//when
		ReservationCancelRequest cancelRequest
			= new ReservationCancelRequestFactory().create(request, reserve.id());
		reservationService.cancel(cancelRequest);

		//then
		ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) customThreadPoolTaskExecutor;
		executor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);
		verify(performanceCancelEventService, times(1)).canceled(any());
	}

}