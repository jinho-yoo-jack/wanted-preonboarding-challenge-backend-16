package com.wanted.preonboarding.ticket.application.common.exception;

public class PaymentFailedException extends ApiException {
    public PaymentFailedException(ExceptionStatus exceptionStatus) {
        super(exceptionStatus);
    }
}
