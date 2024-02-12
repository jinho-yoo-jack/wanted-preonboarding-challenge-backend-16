package com.wanted.preonboarding.core.exception;

import com.wanted.preonboarding.core.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class DisableDiscountException extends WantedTicketException {

    public DisableDiscountException() {
        super(HttpStatus.CONFLICT, ErrorCode.DISABLE_DISCOUNT.getErrorCode(), ErrorCode.DISABLE_DISCOUNT.getMessage());
    }

    public DisableDiscountException(String message) {
        super(HttpStatus.CONFLICT, ErrorCode.DISABLE_DISCOUNT.getErrorCode(), message);
    }
}
