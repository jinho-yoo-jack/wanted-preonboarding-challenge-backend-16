package com.wanted.preonboarding.ticket.domain.dto.response;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PerformanceListResponse {

	private String name;
	private char line;
	private int seat;
	private Date start_date;
	private String isReserve;

	public PerformanceListResponse(String name, char line, int seat, Date start_date, String isReserve) {
		this.name = name;
		this.line = line;
		this.seat = seat;
		this.start_date = start_date;
		this.isReserve = isReserve;
	}
}
