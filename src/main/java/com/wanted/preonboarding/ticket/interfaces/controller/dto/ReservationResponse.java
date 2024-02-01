package com.wanted.preonboarding.ticket.interfaces.controller.dto;

import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.info.ReserveInfo;

import lombok.Builder;

@Builder
public record ReservationResponse(
	int round,
	String performanceName,
	char line,
	int seat,
	UUID performanceId,
	String reservationName,
	String reservationPhoneNumber
) {
	public static ReservationResponse of(ReserveInfo reserveInfo) {
		return ReservationResponse.builder()
			.round(reserveInfo.getRound())
			.performanceName(reserveInfo.getPerformanceName())
			.line(reserveInfo.getSeatInfo().getLine())
			.seat(reserveInfo.getSeatInfo().getSeat())
			.performanceId(reserveInfo.getPerformanceId())
			.reservationName(reserveInfo.getUserInfo().getReservationName())
			.reservationPhoneNumber(reserveInfo.getUserInfo().getReservationPhoneNumber())
			.build();
	}
}
