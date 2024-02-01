package com.wanted.preonboarding.ticket.interfaces.controller.dto;

public record ReservationCancelRequest(
	String reservationName,
	String reservationPhoneNumber,
	String performanceId,
	char line,
	int seat
)
{

}
