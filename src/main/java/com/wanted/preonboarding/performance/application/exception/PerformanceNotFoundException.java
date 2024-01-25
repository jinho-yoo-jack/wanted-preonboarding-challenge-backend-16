package com.wanted.preonboarding.performance.application.exception;

public class PerformanceNotFoundException extends RuntimeException{

    private static final String MESSAGE = "공연을 찾을 수 없습니다.";

    public PerformanceNotFoundException() {
        super(MESSAGE);
    }

    public static String getExceptionMessage() {
        return MESSAGE;
    }
}
