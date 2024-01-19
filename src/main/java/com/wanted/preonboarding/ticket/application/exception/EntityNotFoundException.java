package com.wanted.preonboarding.ticket.application.exception;

public class EntityNotFoundException extends ApiException {

    public EntityNotFoundException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }

}
