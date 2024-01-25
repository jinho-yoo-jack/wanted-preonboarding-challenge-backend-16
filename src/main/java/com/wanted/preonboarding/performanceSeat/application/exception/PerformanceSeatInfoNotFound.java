package com.wanted.preonboarding.performanceSeat.application.exception;

public class PerformanceSeatInfoNotFound extends RuntimeException{

    public PerformanceSeatInfoNotFound() {
        super("공연 좌석을 찾을 수 없습니다.");
    }
}
