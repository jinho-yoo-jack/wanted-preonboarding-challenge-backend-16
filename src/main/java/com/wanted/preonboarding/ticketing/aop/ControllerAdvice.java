package com.wanted.preonboarding.ticketing.aop;

import com.wanted.preonboarding.ticketing.aop.advice.exception.*;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(annotations = RestController.class)
public class ControllerAdvice {

    @ExceptionHandler(NotEnoughBalanceException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughBalanceException(NotEnoughBalanceException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(NotFoundReservationException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException() {
        ErrorCode errorCode = ErrorCode.REQUEST_HAS_NULL;
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(DefaultException.class)
    public ResponseEntity<ErrorResponse> handleDefaultException(DefaultException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(NotFoundReservationsException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundReservationsException(NotFoundReservationsException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(NotFoundPerformanceException.class)
    public ResponseEntity<ErrorResponse> handleNotEnoughBalanceException(NotFoundPerformanceException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(NotFoundPerformanceSeatInfoException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundPerformanceSeatInfoException(NotFoundPerformanceSeatInfoException e) {
        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException() {
        ErrorCode errorCode = ErrorCode.NOT_VALIDATED_PARAM;
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException() {
        ErrorCode errorCode = ErrorCode.NOT_VALIDATED_PARAM;
        ErrorResponse errorResponse = ErrorResponse.from(errorCode);

        return new ResponseEntity<>(errorResponse, errorCode.toHttpStatus());
    }
}
