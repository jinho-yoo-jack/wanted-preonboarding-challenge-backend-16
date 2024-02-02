package com.wanted.preonboarding.ticket.exception;

import jakarta.persistence.EntityNotFoundException;

public class PerformanceNotFoundException extends EntityNotFoundException {
    public PerformanceNotFoundException(String s) {
        super(s);
    }

}
