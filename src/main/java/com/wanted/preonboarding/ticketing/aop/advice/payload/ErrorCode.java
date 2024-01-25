package com.wanted.preonboarding.ticketing.aop.advice.payload;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
public enum ErrorCode {
    NOT_ENOUGH_BALANCE(400, "C001", "결제 금액이 부족합니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ErrorResponse toErrorResponse() {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(this.status)
                .code(this.code)
                .message(this.message)
                .build();
    }

    public HttpStatusCode toHttpStatus() {
        return HttpStatusCode.valueOf(this.status);
    }
}
