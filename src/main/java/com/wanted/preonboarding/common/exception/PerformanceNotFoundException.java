package com.wanted.preonboarding.common.exception;

public class PerformanceNotFoundException extends RuntimeException {
	public PerformanceNotFoundException(){
		super("해당 공연을 찾을 수 없습니다.");
	}
}
