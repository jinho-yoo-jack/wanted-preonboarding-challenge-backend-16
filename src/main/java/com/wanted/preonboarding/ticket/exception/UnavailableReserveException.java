package com.wanted.preonboarding.ticket.exception;

import lombok.Getter;

@Getter
public class UnavailableReserveException extends RuntimeException {
    public UnavailableReserveException(String message) {
        super(message);
    }
}
