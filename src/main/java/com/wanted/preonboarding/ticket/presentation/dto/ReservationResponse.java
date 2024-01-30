package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.domain.vo.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.Reservation;
import com.wanted.preonboarding.ticket.domain.Showing;
import java.util.UUID;

//(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
public record ReservationResponse(
	int round,
	String name,
	int seat,
	char line,
	UUID id,
	String userName,
	String phoneNumber) {

	public static ReservationResponse of(Reservation reservation) {
		Showing showing = reservation.getShowing();
		Performance performance = showing.getPerformance();
		PerformanceSeatInfo performanceSeatInfo = reservation.getPerformanceSeatInfo();
		return new ReservationResponse(
			showing.getRound(),
			performance.getName(),
			performanceSeatInfo.getSeat(),
			performanceSeatInfo.getLine(),
			performance.getId(),
			reservation.getName(),
			reservation.getPhoneNumber()
		);
	}
}
