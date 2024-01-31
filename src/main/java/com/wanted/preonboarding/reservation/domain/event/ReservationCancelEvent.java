package com.wanted.preonboarding.reservation.domain.event;

import com.wanted.preonboarding.reservation.domain.vo.ReserveItem;
import com.wanted.preonboarding.reservation.domain.vo.SeatInfo;
import java.util.UUID;

public record ReservationCancelEvent(
	UUID performId,
	UUID reservationId,
	char line,
	int seat

) {

	public static ReservationCancelEvent create(ReserveItem reserveItem) {
		UUID performId = reserveItem.getItem().getId();
		UUID reservationId = reserveItem.getNo();
		SeatInfo seatInfo = reserveItem.getItem().getSeatInfo();
		return new ReservationCancelEvent(performId,reservationId, seatInfo.getLine(),seatInfo.getSeat());
	}
}




