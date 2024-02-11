package com.wanted.preonboarding.core.exception;

import org.springframework.http.HttpStatus;

public class WantedTicketException extends RuntimeException {

    private HttpStatus status;
    private int code;
    private String message;


    public WantedTicketException(HttpStatus status, int code, String message) {
        super(message);
        this.status = status;
        this.code = code;
        this.message = message;
    }
}
