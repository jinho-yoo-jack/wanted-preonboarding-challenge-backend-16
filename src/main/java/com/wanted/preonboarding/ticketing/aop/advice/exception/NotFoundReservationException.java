package com.wanted.preonboarding.ticketing.aop.advice.exception;

import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;

@Getter
public class NotFoundReservationException extends EntityNotFoundException {
    private final ErrorCode errorCode;

    public NotFoundReservationException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
