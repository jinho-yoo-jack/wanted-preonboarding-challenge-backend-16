package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.PerformanceReservation;
import com.wanted.preonboarding.performance.domain.PerformanceShowing;
import com.wanted.preonboarding.performance.domain.PerformanceShowingObserver;
import com.wanted.preonboarding.performance.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import com.wanted.preonboarding.performance.infrastructure.output.NotificationOutput;
import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceShowingObserverRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ShowingRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class PerformanceCancelEventService {

	private final PerformanceShowingObserverRepository repository;
	private final ShowingRepository showingRepository;
	private final ReservationRepository reservationRepository;

	private final NotificationOutput notificationOutput;

	@EventListener
	public void listenCancelEvent(ReservationCancelEvent event) {
		List<PerformanceShowingObserver> observer = repository.findByShowingId(event.showingId());

		if (observer.isEmpty()) {
			log.info("ReservationCancelEvent.class 에 대한 구독자가 없습니다.");
			return;
		}

		List<UUID> userIds = observer
			.stream()
			.map(PerformanceShowingObserver::getUserId)
			.map(UUID::fromString)
			.collect(Collectors.toList());

		StringBuilder message = reservationCancelMessage(event);
		notificationOutput.reservationCancelNotify(userIds, message.toString());
	}

	private StringBuilder reservationCancelMessage(ReservationCancelEvent event) {
		PerformanceReservation cancel = reservationRepository
			.findByIdAndReservationStatus(event.reservationId(), ReservationStatus.CANCEL)
			.orElseThrow(EntityNotFoundException::new);
		PerformanceShowing showing = cancel.getPerformanceShowing();
		Performance performance = showing.getPerformance();

		//공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보
		StringBuilder message = new StringBuilder();
		message
			.append("공연ID: ").append(showing.getId()).append("\n")
			.append("공연명: ").append(performance.getName()).append("\n")
			.append("회차: ").append(showing.getRound()).append("\n")
			.append("시작 일시: ").append(showing.getStartDate()).append("\n")
			.append("예매 가능한 좌석정보: ").append(cancel.getSeatInfoToString()).append("\n");
		return message;
	}



	@Transactional
	public void subscribe(UUID showingId, UUID userId) {
		PerformanceShowing performanceShowing = showingRepository.findById(showingId)
			.orElseThrow(EntityNotFoundException::new);

		PerformanceShowingObserver observer = PerformanceShowingObserver.create(
			performanceShowing, userId);

		repository.save(observer);
	}
}
