package com.wanted.preonboarding.reservation.application.exception;

public class ReservationAlreadyExists extends RuntimeException {

    public ReservationAlreadyExists() {
        super("이미 예약된 좌석입니다.");
    }
}
