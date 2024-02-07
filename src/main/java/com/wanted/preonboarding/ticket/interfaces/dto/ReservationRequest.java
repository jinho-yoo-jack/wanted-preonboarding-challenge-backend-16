package com.wanted.preonboarding.ticket.interfaces.dto;

public record ReservationRequest(
	String performanceId,
	String performanceName,
	String reservationName,
	String reservationPhoneNumber,
	double amount,
	int round,
	char line,
	int seat,
	int age
) {
}
