package com.wanted.preonboarding.performance.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wanted.preonboarding.performance.application.output.NotificationOutput;
import com.wanted.preonboarding.reservation.application.ReservationService;
import com.wanted.preonboarding.reservation.domain.vo.ReservationStatus;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationCancelRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;
import com.wanted.preonboarding.uitl.basetest.ServiceTest;
import com.wanted.preonboarding.uitl.requestfactory.NotifyMessageFactory;
import com.wanted.preonboarding.uitl.requestfactory.RequestFactory;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;


@DisplayName("이벤트: 예약 취소 - 리스너")
class PerformanceCancelEventServiceTest extends ServiceTest {

	@Autowired
	private PerformService performService;
	@Autowired
	private PerformAdminService performAdminService;
	@Autowired
	private ReservationService reservationService;
	@Autowired
	@Qualifier("customThreadPoolTaskExecutor")
	private Executor customThreadPoolTaskExecutor;

	private UUID userId;
	private UUID performId;
	private ReservedItemResponse reservedItemResponse;
	private ReservationCancelRequest cancelRequest;

	@MockBean
	private NotificationOutput notificationOutput;

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
	public void 예약_취소_시_구독자_알림_발송() throws InterruptedException {
		//given
		performService.subscribe(performId,userId);
		//when
		reservationService.cancel(cancelRequest);
		await();
		//then
		String message = NotifyMessageFactory.reservationCancel(performId);
		verify(
			notificationOutput, times(1)
		).reservationCancelNotify(List.of(userId), message);

	}

	@Test
	public void 예약_취소_시_구독자_알림_발송_실패_예약취소에_영향_없음() throws InterruptedException {
		Mockito.doThrow(new RuntimeException("알림 외부서비스 에러")).when(notificationOutput).reservationCancelNotify(any(),any());

		//given
		performService.subscribe(performId,userId);
		//when
		reservationService.cancel(cancelRequest);
		await();
		//then
		ReservedItemResponse reservation = reservationService.getReservations(
				reservedItemResponse.userName(),
				reservedItemResponse.phoneNumber()
			).get(0);
		assertThat(reservation.status()).isEqualTo(ReservationStatus.CANCEL);

	}

	@Test
	@Transactional
	public void 예약_취소_롤백시_구독자_알림_발송_진행되지_않음() throws InterruptedException {
		//given
		performService.subscribe(performId,userId);
		//when
		reservationService.cancel(cancelRequest);
		TestTransaction.flagForRollback();
		TestTransaction.end();
		await();
		//then
		String message = NotifyMessageFactory.reservationCancel(performId);
		verify(
			notificationOutput, times(0)
		).reservationCancelNotify(List.of(userId), message);

	}

	private void await() throws InterruptedException {
		ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) customThreadPoolTaskExecutor;
		executor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);
	}
}