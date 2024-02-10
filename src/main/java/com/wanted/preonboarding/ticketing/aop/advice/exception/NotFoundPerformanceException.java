package com.wanted.preonboarding.ticketing.aop.advice.exception;

import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class NotFoundPerformanceException extends EntityNotFoundException {
    private final ErrorCode errorCode;
    public NotFoundPerformanceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
