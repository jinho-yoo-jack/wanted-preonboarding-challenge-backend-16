package com.wanted.preonboarding.reservation.framwork.evenadatper;

import com.wanted.preonboarding.performance.framwork.infrastructure.eventAdapter.PerformLister;
import com.wanted.preonboarding.reservation.application.output.EventOutputPort;
import com.wanted.preonboarding.reservation.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.reservation.domain.event.ReserveEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventPublisher implements EventOutputPort {

	private final ApplicationEventPublisher publisher;
	private final PerformLister performLister;

	@Override
	public void cancelEventPublish(ReservationCancelEvent event) {
		publisher.publishEvent(event);
	}

	@Override
	public boolean reserveEventPublish(ReserveEvent evnet) {
		return performLister.isAvailable(evnet);
	}
}
