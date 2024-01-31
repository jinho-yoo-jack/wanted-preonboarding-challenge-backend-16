package com.wanted.preonboarding.performance.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.wanted.preonboarding.ServiceTest;
import com.wanted.preonboarding.performance.PerformanceRequestFactory;
import com.wanted.preonboarding.performance.ReservationRequestFactory;
import com.wanted.preonboarding.performance.domain.event.PerformanceCancelEventListener;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import com.wanted.preonboarding.performance.infrastructure.output.NotificationOutput;
import com.wanted.preonboarding.performance.presentation.dto.PerformanceRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.performance.presentation.dto.ReservationResponse;
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


@DisplayName("이벤트: 예약 취소 - 리스너")
class PerformanceCancelEventServiceTest extends ServiceTest {

	@Autowired
	private PerformanceCancelEventListener performanceCancelEventService;

	@Autowired
	private ShowingAdminService showingAdminService;

	@Autowired
	private ReservationService reservationService;

	@Autowired
	@Qualifier("customThreadPoolTaskExecutor")
	private Executor customThreadPoolTaskExecutor;
	private final PerformanceRequestFactory performanceRequestFactory = new PerformanceRequestFactory();
	private final PerformanceRequest performanceRequest = performanceRequestFactory.create();
	private final ReservationRequestFactory factory = new ReservationRequestFactory();
	private final UUID userId = UUID.randomUUID();
	private UUID showingId;
	private ReservationRequest request;
	@BeforeEach
	void setUp() {
		showingId = showingAdminService.register(performanceRequest);
		request = factory.create(showingId);
	}

	@MockBean
	private NotificationOutput notificationOutput;

	@Test
	public void 예약_취소_시_구독자_알림_발송() throws InterruptedException {
		//given
		performanceCancelEventService.subscribe(showingId, userId);
		ReservationResponse reserve = reservationService.reserve(request);

		//when
		reservationService.cancel(reserve.id());

		//then
		StringBuilder message = new StringBuilder()
			.append("공연ID: ").append(request.showingId()).append("\n")
			.append("공연명: ").append(reserve.name()).append("\n")
			.append("회차: ").append(request.round()).append("\n")
			.append("시작 일시: ").append(performanceRequest.startDate()).append("\n")
			.append("예매 가능한 좌석정보: ").append(reserve.line() + ":" + reserve.seat()).append("\n");

		ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) customThreadPoolTaskExecutor;
		executor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);

		verify(notificationOutput, times(1)).reservationCancelNotify(List.of(userId),
			message.toString());

	}

	@Test
	public void 예약_취소_시_구독자_알림_발송_실패_예약취소에_영향_없음() throws InterruptedException {
		Mockito.doThrow(new RuntimeException("알림 외부서비스 에러")).when(notificationOutput).reservationCancelNotify(any(),any());
		//given
		performanceCancelEventService.subscribe(showingId, userId);
		ReservationResponse reserve = reservationService.reserve(request);

		//when
		reservationService.cancel(reserve.id());

		ThreadPoolTaskExecutor executor = (ThreadPoolTaskExecutor) customThreadPoolTaskExecutor;
		executor.getThreadPoolExecutor().awaitTermination(1, TimeUnit.SECONDS);

		//then
		ReservationResponse reservation = reservationService.getReservation(reserve.userName(),
			reserve.phoneNumber()).get(0);
		assertThat(reservation.status()).isEqualTo(ReservationStatus.CANCEL);

	}
}