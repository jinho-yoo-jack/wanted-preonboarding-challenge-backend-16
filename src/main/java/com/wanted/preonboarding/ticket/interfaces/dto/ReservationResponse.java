package com.wanted.preonboarding.ticket.interfaces.dto;

import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.dto.ReservationInfo;

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
	public static ReservationResponse of(ReservationInfo reservationInfo) {
		return ReservationResponse.builder()
			.round(reservationInfo.getRound())
			.performanceName(reservationInfo.getPerformanceName())
			.line(reservationInfo.getSeatInfo().getLine())
			.seat(reservationInfo.getSeatInfo().getSeat())
			.performanceId(reservationInfo.getPerformanceId())
			.reservationName(reservationInfo.getUserInfo().getReservationName())
			.reservationPhoneNumber(reservationInfo.getUserInfo().getReservationPhoneNumber())
			.build();
	}
}
