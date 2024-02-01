package com.wanted.preonboarding.common.exception;

public class PerformanceSeatInfoNotFoundException extends RuntimeException {
	public PerformanceSeatInfoNotFoundException() {
		super("해당 좌석은 예매된 상태가 아닙니다.");
	}
}
