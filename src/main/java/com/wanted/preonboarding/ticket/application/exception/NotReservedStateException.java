package com.wanted.preonboarding.ticket.application.exception;

public class NotReservedStateException extends IllegalStateException {

    public NotReservedStateException(String s) {
        super(s);
    }
}
