package com.wanted.preonboarding.ticket.application.exception;

public class ServiceFailedException extends ApiException {
    public ServiceFailedException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
