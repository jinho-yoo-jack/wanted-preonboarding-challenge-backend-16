package com.wanted.preonboarding.performance.application.exception;

public class PerformanceNotFoundException extends RuntimeException{
    public PerformanceNotFoundException() {
        super("공연을 찾을 수 없습니다.");
    }
}
