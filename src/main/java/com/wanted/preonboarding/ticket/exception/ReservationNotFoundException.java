package com.wanted.preonboarding.ticket.exception;

import jakarta.persistence.EntityNotFoundException;

public class ReservationNotFoundException extends EntityNotFoundException {
    public ReservationNotFoundException(String s) {
        super(s);
    }
}