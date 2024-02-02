package com.wanted.preonboarding.reservation.application.output;

import com.wanted.preonboarding.reservation.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.reservation.domain.event.ReserveEvent;
import com.wanted.preonboarding.reservation.framwork.evenadatper.dto.ReserveEventResult;

public interface EventOutputPort {
	void cancelEventPublish(ReservationCancelEvent event);

	ReserveEventResult reserveEventPublish(ReserveEvent reserveEvent);

}
