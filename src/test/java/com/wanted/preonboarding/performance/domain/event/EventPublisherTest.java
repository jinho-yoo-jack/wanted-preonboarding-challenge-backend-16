package com.wanted.preonboarding.performance.domain.event;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.application.PerformanceCancelEventListener;
import com.wanted.preonboarding.performance.application.PerformanceCancelEventService;
import com.wanted.preonboarding.performance.application.ReservationService;
import com.wanted.preonboarding.performance.application.ShowingAdminService;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationResponse;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@DisplayName("이벤트: 예약 취소 - 발행")
public class EventPublisherTest extends ServiceTest {

	@Autowired
	private PerformanceCancelEventListener performanceCancelEventListener;

	@Autowired
	private ShowingAdminService showingAdminService;

	@Autowired
	private ReservationService reservationService;

	@MockBean
	private PerformanceCancelEventService performanceCancelEventService;

	@Autowired
	@Qualifier("customThreadPoolTaskExecutor")
	private Executor customThreadPoolTaskExecutor;

	@Test
	public void 이벤트_발행() throws InterruptedException {
		PerformanceRequestFactory performanceRequestFactory = new PerformanceRequestFactory();
		PerformanceRequest performanceRequest = performanceRequestFactory.create();
		UUID showingId = showingAdminService.register(performanceRequest);
		ReservationRequestFactory factory = new ReservationRequestFactory();
		ReservationRequest request = factory.create(showingId);
		ReservationResponse reserve = reservationService.reserve(request);
		UUID userId = UUID.randomUUID();

		//given
		performanceCancelEventListener.subscribe(showingId,userId);
		//when
		reservationService.cancel(reserve.id());

		//then
		ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) customThreadPoolTaskExecutor;
		executor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);
		verify(performanceCancelEventService, times(1)).canceled(any());
	}

}