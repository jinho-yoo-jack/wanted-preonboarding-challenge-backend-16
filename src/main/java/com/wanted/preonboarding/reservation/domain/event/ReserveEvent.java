package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.reservation.domain.vo.SeatInfo;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReserveEvent {
	private UUID itemId;
	private char line;
	private int seat;
	public static ReserveEvent create(UUID itemId, SeatInfo seatInfo) {
		return new ReserveEvent(itemId,seatInfo.getLine(),seatInfo.getSeat());
	}
}
