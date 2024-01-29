package com.wanted.preonboarding.ticket.exception.badrequest;

import com.wanted.preonboarding.ticket.exception.CustomException;

public class BadRequestException extends CustomException {

    public BadRequestException(String message) {
        super(message);
    }
}
