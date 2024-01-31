package com.wanted.preonboarding.uitl.requestfactory;

import com.wanted.preonboarding.uitl.testdata.TestPerformance;
import java.util.UUID;

public class NotifyMessageFactory {

	private static TestPerformance testPerformance = new TestPerformance();
	public static String reservationCancel(UUID performId) {
		StringBuilder message = new StringBuilder()
			.append("공연ID: ").append(performId).append("\n")
			.append("공연명: ").append(RequestFactory.getPerformRegister().name()).append("\n")
			.append("회차: ").append(testPerformance.getRound()).append("\n")
			.append("시작 일시: ").append(RequestFactory.getPerformRegister().startDate()).append("\n")
			.append("예매 가능한 좌석정보: ").append(RequestFactory.getReservation(performId).line() + "라인" +" "+RequestFactory.getReservation(performId).seat()+"시트").append("\n");
		return message.toString();
	}
}
