package com.wanted.preonboarding.ticket.domain.exception;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.domain.exception.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionController {
    @ExceptionHandler(PerformanceDisableException.class)
    public ResponseEntity<ResponseHandler<String>> returnPerformanceDisableException(PerformanceDisableException e)
    {
        return ResponseEntity
                .badRequest()
                .body(ResponseHandler.<String>builder()
                        .message(HttpStatus.BAD_REQUEST.name())
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .data(e.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(SeatDisableException.class)
    public ResponseEntity<ResponseHandler<String>> returnSeatDisableException(SeatDisableException e)
    {
        return ResponseEntity
                .badRequest()
                .body(ResponseHandler.<String>builder()
                        .message(HttpStatus.BAD_REQUEST.name())
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .data(e.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(PayPriceIsNotEnoughException.class)
    public ResponseEntity<ResponseHandler<String>> returnPayPriceIsNotEnoughException(PayPriceIsNotEnoughException e)
    {
        return ResponseEntity
                .badRequest()
                .body(ResponseHandler.<String>builder()
                        .message(HttpStatus.BAD_REQUEST.name())
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .data(e.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(ThisSeatDisableException.class)
    public ResponseEntity<ResponseHandler<String>> returnThisSeatDisableException(ThisSeatDisableException e)
    {
        return ResponseEntity
                .badRequest()
                .body(ResponseHandler.<String>builder()
                        .message(HttpStatus.BAD_REQUEST.name())
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .data(e.getMessage())
                        .build()
                );
    }
    @ExceptionHandler(ReservationNotFountException.class)
    public ResponseEntity<ResponseHandler<String>> returnReservationNotFountException(ReservationNotFountException e)
    {
        return ResponseEntity
                .badRequest()
                .body(ResponseHandler.<String>builder()
                        .message(HttpStatus.BAD_REQUEST.name())
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .data(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(PerformanceNotFoundException.class)
    public ResponseEntity<ResponseHandler<String>> returnPerformanceNotFoundException(PerformanceNotFoundException e)
    {
        return ResponseEntity
                .badRequest()
                .body(ResponseHandler.<String>builder()
                        .message(HttpStatus.BAD_REQUEST.name())
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .data(e.getMessage())
                        .build()
                );
    }





}