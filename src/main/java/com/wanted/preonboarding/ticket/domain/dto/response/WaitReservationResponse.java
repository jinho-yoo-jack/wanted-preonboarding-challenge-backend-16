package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.WaitingList;
import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WaitReservationResponse {

	private UUID id;
	private int round;
	private char line;
	private int seat;
	private String name;
	private String phoneNumber;

	public static WaitReservationResponse of(WaitingList waitingList) {
		WaitReservationResponse response = new WaitReservationResponse();
		response.id = waitingList.getPerformanceId();
		response.round = waitingList.getRound();
		response.line = waitingList.getLine();
		response.seat = waitingList.getSeat();
		response.name = waitingList.getName();
		response.phoneNumber = waitingList.getPhoneNumber();
		return response;
	}
}
