package com.wanted.preonboarding.ticket.exception;

import jakarta.persistence.EntityNotFoundException;

public class PerformanceSeatInfoNotFound extends EntityNotFoundException {

    public PerformanceSeatInfoNotFound(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
