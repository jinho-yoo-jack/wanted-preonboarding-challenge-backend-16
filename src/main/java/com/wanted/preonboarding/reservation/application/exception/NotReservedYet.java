package com.wanted.preonboarding.reservation.application.exception;

public class NotReservedYet extends RuntimeException{

    private static final String MESSAGE = "아직 예약이 없습니다.";

    public NotReservedYet() {
        super(MESSAGE);
    }

    public static String getExceptionMessage() {
        return MESSAGE;
    }
}
