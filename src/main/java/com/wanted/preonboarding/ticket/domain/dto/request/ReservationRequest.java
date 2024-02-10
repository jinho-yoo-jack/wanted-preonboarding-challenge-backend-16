package com.wanted.preonboarding.ticket.domain.dto.request;

import java.util.UUID;
import lombok.Data;

@Data
public class ReservationRequest {
	private UUID id;
	private int round;
	private char line;
	private int seat;
}
