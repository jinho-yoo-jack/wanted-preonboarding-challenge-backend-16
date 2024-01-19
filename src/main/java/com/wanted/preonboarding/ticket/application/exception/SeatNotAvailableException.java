package com.wanted.preonboarding.ticket.application.exception;

public class SeatNotAvailableException extends ApiException {
    public SeatNotAvailableException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
