package com.wanted.preonboarding.reservation.presentation.exceptionHandler;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.reservation.application.exception.ReservationAlreadyExists;
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
}
