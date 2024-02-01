package com.wanted.preonboarding.ticket.interfaces.controller.dto;

public record CustomerContactRequest(
	String reservationName,
	String reservationPhoneNumber
) {
}
