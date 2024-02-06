package com.wanted.preonboarding.ticket.application.exception;

public class AlreadyReservedStateException extends IllegalStateException {

    public AlreadyReservedStateException(String s) {
        super(s);
    }
}
