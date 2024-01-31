package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.presentation.PerformanceReservation;
import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.PerformSubscribe;
import com.wanted.preonboarding.performance.domain.event.CancelEventUsecase;
import com.wanted.preonboarding.performance.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import com.wanted.preonboarding.performance.infrastructure.output.NotificationOutput;
import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceShowingObserverRepository;
import com.wanted.preonboarding.performance.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PerformanceCancelEventService implements CancelEventUsecase {
	private final PerformanceShowingObserverRepository repository;
	private final ReservationRepository reservationRepository;
	private final NotificationOutput notificationOutput;

	public void canceled(ReservationCancelEvent event) {
		List<PerformSubscribe> observer = repository.findByPerformId(event.showingId());

		if (observer.isEmpty()) {
			log.info("ReservationCancelEvent.class 에 대한 구독자가 없습니다.");
			return;
		}

		List<UUID> userIds = observer
			.stream()
			.map(PerformSubscribe::getUserId)
			.map(UUID::fromString)
			.collect(Collectors.toList());

		StringBuilder message = reservationCancelMessage(event);
		notificationOutput.reservationCancelNotify(userIds, message.toString());
	}

	public StringBuilder reservationCancelMessage(ReservationCancelEvent event) {
		log.info("reservationID {}",event.reservationId());
		PerformanceReservation cancel = reservationRepository
			.findByIdAndReservationStatus(event.reservationId(), ReservationStatus.CANCEL)
			.orElseThrow(EntityNotFoundException::new);
		Perform showing = cancel.getPerform();
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
}
