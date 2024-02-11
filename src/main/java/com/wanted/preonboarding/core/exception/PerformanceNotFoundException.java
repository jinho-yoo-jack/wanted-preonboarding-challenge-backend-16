package com.wanted.preonboarding.core.exception;

import com.wanted.preonboarding.core.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;


public class PerformanceNotFoundException extends WantedTicketException {

    public PerformanceNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.PERFORMANCE_NOT_FOUND.getErrorCode(), ErrorCode.PERFORMANCE_NOT_FOUND.getMessage());
    }

    public PerformanceNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.PERFORMANCE_NOT_FOUND.getErrorCode(), message);
    }
}
