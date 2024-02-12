package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ErrorResponseHandler;
import com.wanted.preonboarding.ticket.domain.exception.BadRequestException;
import com.wanted.preonboarding.ticket.domain.exception.NotFoundException;
import com.wanted.preonboarding.ticket.domain.exception.PaymentException;
import com.wanted.preonboarding.ticket.domain.exception.ForbiddenException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseHandler> handleNotFoundException(final NotFoundException e) {
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
    public ResponseEntity<ErrorResponseHandler> handleBadRequestException(final BadRequestException e) {
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
    public ResponseEntity<ErrorResponseHandler> handlePaymentException(final PaymentException e) {
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
    public ResponseEntity<ErrorResponseHandler> handleForbiddenException(final ForbiddenException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorResponseHandler.builder()
                        .statusCode(HttpStatus.FORBIDDEN)
                        .code("FORBIDDEN")
                        .message(e.getMessage())
                        .build()
                );
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseHandler.builder()
                        .statusCode(HttpStatus.NOT_FOUND)
                        .code("NO_HANDLER_FOUND")
                        .message("페이지가 존재하지 않습니다.")
                        .build()
                );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseHandler.builder()
                        .statusCode(HttpStatus.BAD_REQUEST)
                        .code("METHOD_NOT_SUPPORTED")
                        .message("지원되지 않는 HTTP METHOD 입니다.")
                        .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(final Exception e) {
        e.printStackTrace();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseHandler.builder()
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                        .code("INTERNAL_SERVER_ERROR")
                        .message("에러가 발생했습니다.")
                        .build()
                );
    }
}
