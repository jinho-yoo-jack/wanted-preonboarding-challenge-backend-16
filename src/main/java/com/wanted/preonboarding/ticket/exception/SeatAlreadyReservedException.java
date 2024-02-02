package com.wanted.preonboarding.ticket.exception;

public class SeatAlreadyReservedException extends IllegalArgumentException{
    public SeatAlreadyReservedException(String s) {
        super(s);
    }
}
