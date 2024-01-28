package com.wanted.preonboarding.ticket.application.common.exception;

public class SeatNotAvailableException extends ApiException {
    public SeatNotAvailableException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
