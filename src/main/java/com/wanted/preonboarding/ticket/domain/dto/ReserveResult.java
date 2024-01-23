package com.wanted.preonboarding.ticket.domain.dto;

import java.util.UUID;

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

}
