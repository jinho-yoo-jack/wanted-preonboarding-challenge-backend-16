package com.wanted.preonboarding.reservation.application.exception;

public class ReservationAlreadyExists extends RuntimeException {

    private static final String MESSAGE = "이미 예약된 좌석입니다.";

    public ReservationAlreadyExists() {
        super(MESSAGE);
    }

    public static String getExceptionMessage() {
        return MESSAGE;
    }
}
