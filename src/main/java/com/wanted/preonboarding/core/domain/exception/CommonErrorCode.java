package com.wanted.preonboarding.core.domain.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {
    NO_HANDLER_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 API입니다."),
    METHOD_NOT_SUPPORTED(HttpStatus.BAD_REQUEST, "지원되지 않는 HTTP METHOD 입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.NOT_FOUND, "에러가 발생했습니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}