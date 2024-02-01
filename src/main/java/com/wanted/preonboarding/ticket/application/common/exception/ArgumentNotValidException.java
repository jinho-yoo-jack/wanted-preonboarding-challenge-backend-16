package com.wanted.preonboarding.ticket.application.common.exception;

public class ArgumentNotValidException extends ApiException {
    public ArgumentNotValidException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
