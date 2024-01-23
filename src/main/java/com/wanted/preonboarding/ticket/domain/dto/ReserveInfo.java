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
public class ReserveInfo {
	// 공연 및 전시 정보 + 예약자 정보
	private UUID performanceId;
	private String reservationName;
	private String reservationPhoneNumber;
	private String reservationStatus; // 예약; 취소;
	private long amount;
	private int round;
	private String line;
	private int seat;
	private int age = 0;
}