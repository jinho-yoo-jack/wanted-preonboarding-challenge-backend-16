package com.wanted.preonboarding.ticket.application.exception;

import java.awt.event.FocusEvent;

public class ArgumentNotValidException extends ApiException {
    public ArgumentNotValidException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
