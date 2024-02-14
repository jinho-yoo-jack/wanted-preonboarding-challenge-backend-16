package com.wanted.preonboarding.ticket.exception;

public class NotEnoughBalanceException extends RuntimeException {

    public NotEnoughBalanceException(ErrorCode errorcode) {
        super(errorcode.getMessage());
    }
}
