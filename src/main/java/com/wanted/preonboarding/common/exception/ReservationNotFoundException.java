package com.wanted.preonboarding.common.exception;

public class ReservationNotFoundException extends RuntimeException {
	public ReservationNotFoundException() {
		super("예매 내역이 존재하지 않습니다.");
	}
}
