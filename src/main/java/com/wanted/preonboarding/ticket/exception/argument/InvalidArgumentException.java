package com.wanted.preonboarding.ticket.exception.argument;

import com.wanted.preonboarding.ticket.exception.CustomException;

public class InvalidArgumentException extends CustomException {

    private static final String message = "유효하지 않은 매개변수입니다.";

    public InvalidArgumentException(String message) {
        super(message);
    }

    public InvalidArgumentException() {
        super(message);
    }
}
