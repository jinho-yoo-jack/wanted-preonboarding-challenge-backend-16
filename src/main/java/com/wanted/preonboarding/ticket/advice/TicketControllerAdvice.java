package com.wanted.preonboarding.ticket.advice;

import com.wanted.preonboarding.ticket.exception.*;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;

@RestControllerAdvice(basePackages = "com.wanted.preonboarding.ticket")
@EnableAutoConfiguration(exclude = ErrorMvcAutoConfiguration.class)
public class TicketControllerAdvice {
    @ExceptionHandler(NoAvailableCancelSeatException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> noAvailableCancelSeatExceptionHandler(NoAvailableCancelSeatException e) {
        Problem problem = Problem.builder()
                .withStatus(Status.BAD_REQUEST)
                .withTitle(Status.BAD_REQUEST.getReasonPhrase())
                .withDetail(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problem);
    }

    @ExceptionHandler(NotEnoughBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> notEnoughBalanceExceptionHandler(NotEnoughBalanceException e) {
        Problem problem = Problem.builder()
                .withStatus(Status.BAD_REQUEST)
                .withTitle(Status.BAD_REQUEST.getReasonPhrase())
                .withDetail(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problem);
    }

    @ExceptionHandler(PerformanceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> performanceNotFoundExceptionHandler(PerformanceNotFoundException e) {
        Problem problem = Problem.builder()
                .withStatus(Status.NOT_FOUND)
                .withTitle(Status.NOT_FOUND.getReasonPhrase())
                .withDetail(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problem);
    }

    @ExceptionHandler(PerformanceSeatInfoNotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> performanceSeatInfoNotFoundExceptionHandler(PerformanceSeatInfoNotFound e) {
        Problem problem = Problem.builder()
                .withStatus(Status.NOT_FOUND)
                .withTitle(Status.NOT_FOUND.getReasonPhrase())
                .withDetail(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problem);
    }

    @ExceptionHandler(ReservationNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> reservationNotFoundExceptionHandler(ReservationNotFoundException e) {
        Problem problem = Problem.builder()
                .withStatus(Status.NOT_FOUND)
                .withTitle(Status.NOT_FOUND.getReasonPhrase())
                .withDetail(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(problem);
    }

    @ExceptionHandler(SeatAlreadyReservedException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> seatAlreadyReservedException(SeatAlreadyReservedException e) {
        Problem problem = Problem.builder()
                .withStatus(Status.BAD_REQUEST)
                .withTitle(Status.BAD_REQUEST.getReasonPhrase())
                .withDetail(e.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(problem);
    }
}
