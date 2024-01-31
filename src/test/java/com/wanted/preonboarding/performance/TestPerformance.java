package com.wanted.preonboarding.performance;

import com.wanted.preonboarding.performance.domain.vo.PerformanceType;
import java.time.LocalDate;

public class TestPerformance {

	private String name = "공연이름";
	private int price = 100000;
	private int round = 3;
	private PerformanceType type = PerformanceType.CONCERT;
	private LocalDate start_Date = LocalDate.now();
	private boolean isReserve = true;

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getRound() {
		return round;
	}

	public PerformanceType getType() {
		return type;
	}

	public LocalDate getStart_Date() {
		return start_Date;
	}

	public boolean isReserve() {
		return isReserve;
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
