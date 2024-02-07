package com.wanted.preonboarding.ticket.interfaces.dto;

public record CustomerContactRequest(
	String reservationName,
	String reservationPhoneNumber
) {
}
