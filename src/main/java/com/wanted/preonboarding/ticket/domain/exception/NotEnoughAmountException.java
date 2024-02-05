package com.wanted.preonboarding.ticket.domain.exception;

public class NotEnoughAmountException extends RuntimeException {
    public NotEnoughAmountException(String message) {
        super(message);
    }
}
