package com.wanted.preonboarding.ticket.exception;

public class InsufficientPaymentException extends RuntimeException {

    public InsufficientPaymentException(final String message) {
        super(message);
    }
}
