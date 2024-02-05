package com.wanted.preonboarding.ticket.domain.exception;

public class AlreadyReservationException extends RuntimeException {
    public AlreadyReservationException(String message) {
        super(message);
    }

}
