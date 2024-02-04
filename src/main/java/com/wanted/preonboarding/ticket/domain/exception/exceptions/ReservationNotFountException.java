package com.wanted.preonboarding.ticket.domain.exception.exceptions;

public class ReservationNotFountException extends RuntimeException{
    public ReservationNotFountException() {
        super("해당 예약을 찾을 수 없습니다.");
    }
}

