package com.wanted.preonboarding.performance.domain.event;

import java.util.UUID;

public record ReservationCancelEvent(
	UUID showingId,
	UUID reservationId
) {

}




