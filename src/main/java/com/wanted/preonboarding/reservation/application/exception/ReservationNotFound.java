package com.wanted.preonboarding.reservation.application.exception;

public class ReservationNotFound extends RuntimeException{

    private static final String MESSAGE = "예약이 존재하지 않습니다.";

    public ReservationNotFound() {
        super(MESSAGE);
    }

    public static String getExceptionMessage() {
        return MESSAGE;
    }
}
