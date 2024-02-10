package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.WaitingList;
import lombok.Data;

@Data
public class ReservationResponse {
	private String name;
	private String phoneNumber;

	public static ReservationResponse of(WaitingList waitingList) {
		ReservationResponse response = new ReservationResponse();
		response.name = waitingList.getName();
		response.phoneNumber = waitingList.getPhoneNumber();
		return response;
	}
}
