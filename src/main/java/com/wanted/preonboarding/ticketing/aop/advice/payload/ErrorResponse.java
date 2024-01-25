package com.wanted.preonboarding.ticketing.aop.advice.payload;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ErrorResponse {
    private LocalDateTime timestamp;
    private int status;
    private String code;
    private String message;

    public static ErrorResponse from(ErrorCode errorCode) {
        return errorCode.toErrorResponse();
    }
}
