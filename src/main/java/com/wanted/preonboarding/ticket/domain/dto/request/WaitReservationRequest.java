package com.wanted.preonboarding.ticket.domain.dto.request;

import java.util.UUID;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WaitReservationRequest {
	private UUID id;
	private int round;
	private char line;
	private int seat;
	private String name;
	private String phoneNumber;
}
