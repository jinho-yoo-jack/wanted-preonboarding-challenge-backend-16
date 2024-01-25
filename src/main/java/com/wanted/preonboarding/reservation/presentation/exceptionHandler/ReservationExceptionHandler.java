package com.wanted.preonboarding.reservation.presentation.exceptionHandler;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.reservation.application.exception.NotReservedYet;
import com.wanted.preonboarding.reservation.application.exception.ReservationAlreadyExists;
import com.wanted.preonboarding.reservation.application.exception.ReservationNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ReservationExceptionHandler {

    @ExceptionHandler(ReservationAlreadyExists.class)
    public ResponseHandler<Object> reservationAlreadyExists() {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.BAD_REQUEST)
                .message(ReservationAlreadyExists.getExceptionMessage())
                .build();
    }

    @ExceptionHandler(NotReservedYet.class)
    public ResponseHandler<Object> notReservedYet() {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .message(NotReservedYet.getExceptionMessage())
                .build();
    }

    @ExceptionHandler(ReservationNotFound.class)
    public ResponseHandler<Object> reservationNotFound() {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.NOT_FOUND)
                .message(ReservationNotFound.getExceptionMessage())
                .build();
    }
}
