package com.wanted.preonboarding.common.exception;

public class ReservationAlreadyExistsException extends RuntimeException {
	public ReservationAlreadyExistsException() {
		super("이미 예약된 좌석입니다.");
	}
}
