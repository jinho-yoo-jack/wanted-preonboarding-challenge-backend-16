package com.wanted.preonboarding.performance.presentation.exceptionHandler;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.performance.application.exception.PerformanceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PerformanceExceptionHandler {

    @ExceptionHandler(PerformanceNotFoundException.class)
    public ResponseHandler<Object> performanceNotFoundException() {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .message(PerformanceNotFoundException.getExceptionMessage())
                .build();
    }
}
