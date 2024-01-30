package com.wanted.preonboarding.performance.domain.event;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher {

	private final ApplicationEventPublisher publisher;

	public void cancelEventPublish(UUID showingId, UUID reservationId) {
		ReservationCancelEvent event = new ReservationCancelEvent(showingId,reservationId);
		publisher.publishEvent(event);
	}
}
