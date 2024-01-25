package com.wanted.preonboarding.ticketing.aop.advice.exception;

import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import lombok.Getter;

@Getter
public class DefaultException extends RuntimeException {
    private final ErrorCode errorCode;

    public DefaultException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
