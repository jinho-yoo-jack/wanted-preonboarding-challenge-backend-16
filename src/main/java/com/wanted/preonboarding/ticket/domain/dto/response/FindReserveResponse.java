package com.wanted.preonboarding.ticket.domain.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class FindReserveResponse {

	private int round;
	private String performanceName;
	private char line;
	private int seat;
	private UUID id;

	@QueryProjection
	public FindReserveResponse(int round, String performanceName, char line, int seat, UUID id) {
		this.round = round;
		this.performanceName = performanceName;
		this.line = line;
		this.seat = seat;
		this.id = id;
	}
}
