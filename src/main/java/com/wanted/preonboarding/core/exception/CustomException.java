package com.wanted.preonboarding.core.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {
    private final ExceptionCode exceptionCode;
    public CustomException() {
        super(ExceptionCode.UNKNOWN_EXCEPTION.getMessage());
        this.exceptionCode = ExceptionCode.UNKNOWN_EXCEPTION;
    }
    public CustomException(String message) {
        super(message);
        this.exceptionCode = ExceptionCode.UNKNOWN_EXCEPTION;
    }
    public CustomException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }
    public CustomException(ExceptionCode exceptionCode, String message) {
        super(message);
        this.exceptionCode = exceptionCode;
    }
}
