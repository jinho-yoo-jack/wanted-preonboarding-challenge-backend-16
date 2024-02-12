package com.wanted.preonboarding.core.exception;

import com.wanted.preonboarding.core.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class PaymentAmountNotCorrectException extends WantedTicketException {

    public PaymentAmountNotCorrectException() {
        super(HttpStatus.CONFLICT, ErrorCode.PAYMENT_AMOUNT_NOT_CORRECT.getErrorCode(), ErrorCode.PAYMENT_AMOUNT_NOT_CORRECT.getMessage());
    }

    public PaymentAmountNotCorrectException(String message) {
        super(HttpStatus.CONFLICT, ErrorCode.PAYMENT_AMOUNT_NOT_CORRECT.getErrorCode(), message);
    }
}
