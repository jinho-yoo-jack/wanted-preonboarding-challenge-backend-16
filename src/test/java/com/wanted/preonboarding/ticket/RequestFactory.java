package com.wanted.preonboarding.ticket;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceType;
import java.time.LocalDate;

public class RequestFactory {

	public static PerformanceRequest getPerformanceRequest() {
		String name = "공연이름";
		int price = 100000;
		int round = 3;
		PerformanceType type = PerformanceType.CONCERT;
		LocalDate start_Date = LocalDate.now();
		boolean isReserve = true;
		PerformanceRequest performanceRequest = new PerformanceRequest(name, price, round, type, start_Date,
			isReserve);
		return performanceRequest;
	}
}
