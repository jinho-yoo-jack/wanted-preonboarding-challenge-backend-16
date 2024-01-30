package com.wanted.preonboarding.performance.presentation.dto;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.vo.PerformanceSeatInfo;
import com.wanted.preonboarding.performance.domain.PerformanceReservation;
import com.wanted.preonboarding.performance.domain.PerformanceShowing;
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

	public static ReservationResponse of(PerformanceReservation performanceReservation) {
		PerformanceShowing performanceShowing = performanceReservation.getPerformanceShowing();
		Performance performance = performanceShowing.getPerformance();
		PerformanceSeatInfo performanceSeatInfo = performanceReservation.getPerformanceSeatInfo();
		return new ReservationResponse(
			performanceShowing.getRound(),
			performance.getName(),
			performanceSeatInfo.getSeat(),
			performanceSeatInfo.getLine(),
			performance.getId(),
			performanceReservation.getName(),
			performanceReservation.getPhoneNumber()
		);
	}
}
