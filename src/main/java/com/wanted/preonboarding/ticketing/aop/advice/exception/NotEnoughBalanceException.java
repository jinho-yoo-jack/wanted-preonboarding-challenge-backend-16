package com.wanted.preonboarding.ticketing.aop.advice.exception;

import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import lombok.Getter;

@Getter
public class NotEnoughBalanceException extends IllegalArgumentException {
    private final ErrorCode errorCode;

    public NotEnoughBalanceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
