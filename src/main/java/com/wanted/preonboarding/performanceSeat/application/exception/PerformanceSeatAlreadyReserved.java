package com.wanted.preonboarding.performanceSeat.application.exception;

public class PerformanceSeatAlreadyReserved extends RuntimeException{

    public PerformanceSeatAlreadyReserved() {
        super("이미 예약된 좌석입니다.");
    }
}
