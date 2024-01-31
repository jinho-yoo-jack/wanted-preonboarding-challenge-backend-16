package com.wanted.preonboarding.reservation.application.output;

import com.wanted.preonboarding.reservation.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.reservation.domain.event.ReserveEvent;

public interface EventOutputPort {
	void cancelEventPublish(ReservationCancelEvent event);

	boolean reserveEventPublish(ReserveEvent reserveEvent);
}
