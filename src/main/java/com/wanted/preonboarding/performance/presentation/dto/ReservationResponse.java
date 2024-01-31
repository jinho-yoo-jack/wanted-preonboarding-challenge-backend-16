package com.wanted.preonboarding.performance.presentation.dto;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.presentation.PerformanceReservation;
import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.vo.PerformanceSeatInfo;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import java.util.UUID;

//(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
public record ReservationResponse(
	int round,
	String name,
	int seat,
	char line,
	UUID id,
	String userName,
	String phoneNumber,
	ReservationStatus status) {

	public static ReservationResponse of(PerformanceReservation performanceReservation) {
		Perform perform = performanceReservation.getPerform();
		Performance performance = perform.getPerformance();
		PerformanceSeatInfo performanceSeatInfo = performanceReservation.getPerformanceSeatInfo();
		return new ReservationResponse(
			perform.getRound(),
			performance.getName(),
			performanceSeatInfo.getSeat(),
			performanceSeatInfo.getLine(),
			performanceReservation.getId(),
			performanceReservation.getName(),
			performanceReservation.getPhoneNumber(),
			performanceReservation.getReservationStatus()
		);
	}
}
