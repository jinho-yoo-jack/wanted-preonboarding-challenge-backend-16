package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ErrorResponseHandler;
import com.wanted.preonboarding.ticket.domain.exception.BadRequestException;
import com.wanted.preonboarding.ticket.domain.exception.NotFoundException;
import com.wanted.preonboarding.ticket.domain.exception.PaymentException;
import com.wanted.preonboarding.ticket.domain.exception.ForbiddenException;
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

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseHandler> handleBadRequestException(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseHandler.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .code("BAD_REQUEST")
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponseHandler> handlePaymentException(PaymentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseHandler.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .code("PAY_ERROR")
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ErrorResponseHandler> handleForbiddenException(ForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseHandler.builder()
                        .statusCode(HttpStatus.FORBIDDEN)
                        .code("FORBIDDEN")
                        .message(e.getMessage())
                        .build()
                );
    }


}
