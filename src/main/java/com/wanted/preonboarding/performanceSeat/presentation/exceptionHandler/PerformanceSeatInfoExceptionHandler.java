package com.wanted.preonboarding.performanceSeat.presentation.exceptionHandler;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.performanceSeat.application.exception.PerformanceSeatInfoNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PerformanceSeatInfoExceptionHandler {

    @ExceptionHandler(PerformanceSeatInfoNotFound.class)
    public ResponseHandler<Object> performanceSeatInfoNotFound() {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .message(PerformanceSeatInfoNotFound.getExceptionMessage())
                .build();
    }
}

