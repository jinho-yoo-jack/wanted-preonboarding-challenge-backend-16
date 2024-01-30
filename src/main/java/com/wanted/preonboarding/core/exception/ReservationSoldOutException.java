package com.wanted.preonboarding.core.exception;

public class ReservationSoldOutException extends RuntimeException {

	public ReservationSoldOutException() {
		super("매진된 상품 입니다.");
	}
}
