package com.wanted.preonboarding.ticket.exception.notfound;

import com.wanted.preonboarding.ticket.exception.CustomException;

public class NotFoundException extends CustomException {

    public NotFoundException(String message) {
        super(message);
    }
}
