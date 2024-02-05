package com.wanted.preonboarding.ticket.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;

import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(EntityNotFound.class)
    public ResponseEntity<ResponseHandler<ExceptionResponse>> handleEntityNotFound(final EntityNotFound e) {
        log.error(e.getMessage());
        return ResponseEntity.status(NOT_FOUND)
                .body(ResponseHandler.<ExceptionResponse>builder()
                        .statusCode(NOT_FOUND)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(InsufficientPaymentException.class)
    public ResponseEntity<ResponseHandler<ExceptionResponse>> handleInsufficientPaymentException(
            final InsufficientPaymentException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(BAD_REQUEST)
                .body(ResponseHandler.<ExceptionResponse>builder()
                        .statusCode(BAD_REQUEST)
                        .message(e.getMessage())
                        .build());
    }

    @ExceptionHandler(PerformanceSeatReserveValidationException.class)
    public ResponseEntity<ResponseHandler<ExceptionResponse>> handlePerformanceSeatReserveValidationException(
            final PerformanceSeatReserveValidationException e) {
        log.error(e.getMessage());
        return ResponseEntity.status(NOT_FOUND)
                .body(ResponseHandler.<ExceptionResponse>builder()
                        .statusCode(NOT_FOUND)
                        .message(e.getMessage())
                        .build());
    }
}
