package com.wanted.preonboarding.ticket.advice;

import com.wanted.preonboarding.core.domain.response.ErrorResponse;
import com.wanted.preonboarding.ticket.exception.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "com.wanted.preonboarding.ticket")
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class TicketControllerAdvice {
    @ExceptionHandler(NoAvailableCancelSeatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> noAvailableCancelSeatExceptionHandler(NoAvailableCancelSeatException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> notEnoughBalanceExceptionHandler(NotEnoughBalanceException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler(PerformanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> performanceNotFoundExceptionHandler(PerformanceNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(PerformanceSeatInfoNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> performanceSeatInfoNotFoundExceptionHandler(PerformanceSeatInfoNotFound e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> reservationNotFoundExceptionHandler(ReservationNotFoundException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.NOT_FOUND)
                .statusCode(HttpStatus.NOT_FOUND.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorResponse);
    }

    @ExceptionHandler(SeatAlreadyReservedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> seatAlreadyReservedException(SeatAlreadyReservedException e) {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }
}
