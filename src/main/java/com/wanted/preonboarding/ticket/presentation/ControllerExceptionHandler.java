package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ErrorResponseHandler;
import com.wanted.preonboarding.ticket.domain.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseHandler> handleNotFoundException(NotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseHandler.builder()
                        .statusCode(HttpStatus.NOT_FOUND)
                        .code("NOT_FOUND")
                        .message(e.getMessage())
                        .build()
                );
    }
}
