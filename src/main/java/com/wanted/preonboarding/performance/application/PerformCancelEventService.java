package com.wanted.preonboarding.performance.application;

import com.wanted.preonboarding.performance.application.output.NotificationOutput;
import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.ReservationCancelSubscribe;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.PerformRepository;
import com.wanted.preonboarding.performance.framwork.infrastructure.repository.ReservationCancelSubscribeRepository;
import com.wanted.preonboarding.reservation.domain.event.ReservationCancelEvent;
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
public class PerformCancelEventService {
	private final ReservationCancelSubscribeRepository repository;
	private final PerformRepository performRepository;
	private final NotificationOutput notificationOutput;

	public void canceled(ReservationCancelEvent event) {
		List<ReservationCancelSubscribe> observer = repository.findByPerformId(event.performId());

		if (observer.isEmpty()) {
			log.info("ReservationCancelEvent.class 에 대한 구독자가 없습니다.");
			return;
		}

		List<UUID> userIds = observer
			.stream()
			.map(ReservationCancelSubscribe::getUserId)
			.map(UUID::fromString)
			.collect(Collectors.toList());

		StringBuilder message = reservationCancelMessage(event);
		notificationOutput.reservationCancelNotify(userIds, message.toString());
	}

	public StringBuilder reservationCancelMessage(ReservationCancelEvent event) {
		log.info("reservationID {}",event.reservationId());

		Perform perform = performRepository.findById(event.performId())
			.orElseThrow(EntityNotFoundException::new);

		//공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보
		StringBuilder message = new StringBuilder();
		message
			.append("공연ID: ").append(perform.getId()).append("\n")
			.append("공연명: ").append(perform.getPerformance().getName()).append("\n")
			.append("회차: ").append(perform.getRound()).append("\n")
			.append("시작 일시: ").append(perform.getStartDate()).append("\n")
			.append("예매 가능한 좌석정보: ")
				.append(event.line()).append("라인").append(" ")
				.append(event.seat()).append("시트").append("\n");
		return message;
	}
}
