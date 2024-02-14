package com.wanted.preonboarding.ticket.exception;

import jakarta.persistence.EntityNotFoundException;

public class PerformanceNotFoundException extends EntityNotFoundException {
    public PerformanceNotFoundException(ErrorCode errorCode){
        super(errorCode.getMessage());
    }

}
