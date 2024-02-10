package com.wanted.preonboarding.ticketing.aop.advice.exception;

import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class NotFoundPerformanceSeatInfoException extends EntityNotFoundException {
    private final ErrorCode errorCode;
    public NotFoundPerformanceSeatInfoException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
