package com.wanted.preonboarding.ticket.domain.dto;

import java.util.UUID;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReserveResult {
	// 공연 및 전시 정보 + 예약자 정보 응답
	private int round;
	private String performanceName;
	private UUID performanceId;
	private String line;
	private int seat;
	private int gate;
	private String reservationName;
	private String reservationPhoneNumber;

	public static ReserveResult of(Reservation reservation) {
		return ReserveResult.builder()
			.round(reservation.getRound())
			.performanceName(reservation.getPerformance().getName())
			.performanceId(reservation.getPerformance().getId())
			.gate(reservation.getGate())
			.line(reservation.getLine())
			.seat(reservation.getSeat())
			.gate(reservation.getGate())
			.reservationPhoneNumber(reservation.getPhoneNumber())
			.reservationName(reservation.getName())
			.build();
	}

}
