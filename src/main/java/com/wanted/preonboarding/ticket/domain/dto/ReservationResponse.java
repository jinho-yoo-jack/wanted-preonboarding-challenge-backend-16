package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.Showing;
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
