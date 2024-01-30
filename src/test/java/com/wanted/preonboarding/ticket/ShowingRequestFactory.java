package com.wanted.preonboarding.ticket;

import com.wanted.preonboarding.ticket.domain.vo.PerformanceType;
import com.wanted.preonboarding.ticket.presentation.dto.PerformanceRequest;
import java.time.LocalDate;

public class ShowingRequestFactory {

	private String name = "공연이름";
	private int price = 100000;
	private int round = 3;
	private PerformanceType type = PerformanceType.CONCERT;
	private LocalDate start_Date = LocalDate.now();
	private boolean isReserve = true;

	public PerformanceRequest create() {
		return new PerformanceRequest(name, price, round, type,
			start_Date, isReserve);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setRound(int round) {
		this.round = round;
	}

	public void setType(PerformanceType type) {
		this.type = type;
	}

	public void setStart_Date(LocalDate start_Date) {
		this.start_Date = start_Date;
	}

	public void setReserve(boolean reserve) {
		isReserve = reserve;
	}
}
