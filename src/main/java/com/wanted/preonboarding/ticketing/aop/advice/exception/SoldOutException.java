package com.wanted.preonboarding.ticketing.aop.advice.exception;

import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import lombok.Getter;

@Getter
public class SoldOutException extends IllegalArgumentException {
    private final ErrorCode errorCode;
    public SoldOutException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
