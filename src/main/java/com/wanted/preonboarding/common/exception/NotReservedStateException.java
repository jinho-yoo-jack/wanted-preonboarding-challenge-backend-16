package com.wanted.preonboarding.common.exception;

public class NotReservedStateException extends IllegalStateException {
	public NotReservedStateException() {
		super("예매하지 않은 공연입니다.");
	}
}
