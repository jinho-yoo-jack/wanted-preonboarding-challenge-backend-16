package com.wanted.preonboarding.ticket.exception;

public class NoAvailableCancelSeatException extends RuntimeException {
    public NoAvailableCancelSeatException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
