package com.wanted.preonboarding.reservation.framwork.presentation.dto;

import java.util.UUID;

public record ReservationRequest(
	// 공연 및 전시 정보 + 예약자 정보
	UUID performId,
	String name,
	String userName,
	String phoneNumber,
	int paymentAmount,
	int round,
	char line,
	int seat
) {

}
