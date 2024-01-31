package com.wanted.preonboarding.reservation.framwork.presentation.dto;

import com.wanted.preonboarding.reservation.domain.vo.Item;
import com.wanted.preonboarding.reservation.domain.vo.NamePhone;
import com.wanted.preonboarding.reservation.domain.vo.ReservationStatus;
import com.wanted.preonboarding.reservation.domain.vo.ReserveItem;
import com.wanted.preonboarding.reservation.domain.vo.SeatInfo;
import java.util.UUID;

//(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
public record ReservedItemResponse(
	int round,
	String name,
	int seat,
	char line,
	UUID id,
	String userName,
	String phoneNumber,
	ReservationStatus status) {

	public static ReservedItemResponse of(ReserveItem reserveItem,NamePhone namePhone) {
		Item item = reserveItem.getItem();
		SeatInfo seatInfo = item.getSeatInfo();
		return new ReservedItemResponse(
			item.getRound(),
			item.getName(),
			seatInfo.getSeat(),
			seatInfo.getLine(),
			reserveItem.getNo(),
			namePhone.getName(),
			namePhone.getPhoneNumber(),
			reserveItem.getReservationStatus()
		);
	}
}
