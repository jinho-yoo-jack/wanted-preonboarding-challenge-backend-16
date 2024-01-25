package com.wanted.preonboarding.performanceSeat.application.exception;

public class PerformanceSeatInfoNotFound extends RuntimeException{

    private static final String MESSAGE = "공연 좌석을 찾을 수 없습니다.";

    public PerformanceSeatInfoNotFound() {
        super(MESSAGE);
    }

    public static String getExceptionMessage() {
        return MESSAGE;
    }
}
