package com.wanted.preonboarding.ticket.exception;

public class SeatAlreadyReservedException extends IllegalArgumentException {
    public SeatAlreadyReservedException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
