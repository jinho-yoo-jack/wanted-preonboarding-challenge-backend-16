package com.wanted.preonboarding.ticket.application.exception.advice;

import com.wanted.preonboarding.core.domain.response.ErrorResponse;
import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.wanted.preonboarding.core.domain.response.ResponseHandler.createResponse;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
    public static final String MESSAGE_ERROR_500 = "서버 내부 에러가 발생했습니다.";

    @ExceptionHandler({Exception.class})
    protected ResponseEntity<ResponseHandler<ErrorResponse>> handleException(Exception e) {
        logError(e);
        return createResponse(HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE_ERROR_500, null);
    }

    @ExceptionHandler({ApiException.class})
    protected ResponseEntity<ResponseHandler<ErrorResponse>> handleException(ApiException e) {
        logError(e);
        return createResponse(HttpStatus.valueOf(e.getStatusCode()), e.getMessage(), null);
    }

    private static void logError(Exception e) {
        log.error("{} 예외 발생! {} ", e.getClass().getSimpleName(), e.getLocalizedMessage());
    }


}
