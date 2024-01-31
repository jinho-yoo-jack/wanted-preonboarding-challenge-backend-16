package com.wanted.preonboarding.reservation.framwork.eventadapter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wanted.preonboarding.performance.application.PerformAdminService;
import com.wanted.preonboarding.performance.application.PerformCancelEventService;
import com.wanted.preonboarding.performance.application.PerformService;
import com.wanted.preonboarding.reservation.application.ReservationService;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;
import com.wanted.preonboarding.uitl.basetest.ServiceTest;
import com.wanted.preonboarding.uitl.requestfactory.RequestFactory;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@DisplayName("이벤트: 예약 취소 - 발행")
public class EventPublisherTest extends ServiceTest {

	@Autowired
	private PerformService performService;
	@Autowired
	private PerformAdminService performAdminService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	@Qualifier("customThreadPoolTaskExecutor")
	private Executor customThreadPoolTaskExecutor;

	@MockBean
	private PerformCancelEventService performanceCancelEventService;

	private UUID userId;
	private UUID performId;
	private ReservedItemResponse reservedItemResponse;
	private ReservationCancelRequest cancelRequest;

	@BeforeEach
	void setUp() {
		userId =  UUID.randomUUID();
		//퍼포먼스 저장
		performId = performAdminService.register(RequestFactory.getPerformRegister());
		//퍼포먼스 예약
		reservedItemResponse = reservationService.reserve(RequestFactory.getReservation(performId));
		//취소 request 생성
		cancelRequest = RequestFactory.getCancel(reservedItemResponse.id());
	}

	@Test
	public void 이벤트_발행() throws InterruptedException {
		//given
		performService.subscribe(performId, userId);
		//when
		reservationService.cancel(cancelRequest);
		//then
		await();
		verify(
			performanceCancelEventService, times(1)
		).canceled(any());
	}

	private void await() throws InterruptedException {
		ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) customThreadPoolTaskExecutor;
		executor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);
	}

}