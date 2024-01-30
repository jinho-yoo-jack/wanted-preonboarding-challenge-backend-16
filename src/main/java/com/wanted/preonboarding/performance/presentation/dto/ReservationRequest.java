package com.wanted.preonboarding.performance.presentation.dto;

import java.util.UUID;

public record ReservationRequest(
	// 공연 및 전시 정보 + 예약자 정보
	UUID showingId,
	String reservationName,
	String reservationPhoneNumber,
	long amount,
	int round,
	char line,
	int seat
) {

}
