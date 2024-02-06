package com.wanted.preonboarding.common.exception.custom;

/**
 * ReservationService.cancelReservation 에 예시 사용 방법
 * 
 */
public class CustomNullPointerException extends NullPointerException{
	
	private static final long serialVersionUID = 7202930534759377048L;

	public CustomNullPointerException(String msg) {
		super(msg);
	}
}
