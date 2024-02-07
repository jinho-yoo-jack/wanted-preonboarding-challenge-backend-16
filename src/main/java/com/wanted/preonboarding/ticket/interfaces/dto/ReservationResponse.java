package com.wanted.preonboarding.ticket.interfaces.dto;

import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.dto.ReservationDTO;

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
	public static ReservationResponse of(ReservationDTO reservationDTO) {
		return ReservationResponse.builder()
			.round(reservationDTO.getRound())
			.performanceName(reservationDTO.getPerformanceName())
			.line(reservationDTO.getSeatInfo().getLine())
			.seat(reservationDTO.getSeatInfo().getSeat())
			.performanceId(reservationDTO.getPerformanceId())
			.reservationName(reservationDTO.getUserInfo().getReservationName())
			.reservationPhoneNumber(reservationDTO.getUserInfo().getReservationPhoneNumber())
			.build();
	}
}
