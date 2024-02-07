package com.wanted.preonboarding.ticket.domain.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AlarmMessage {
	private UUID performanceId;
	private String context;

	public static AlarmMessage of(UUID performanceId, String context) {
		return new AlarmMessage(performanceId, context);
	}

	@Override
	public String toString() {
		return context;
	}
}
