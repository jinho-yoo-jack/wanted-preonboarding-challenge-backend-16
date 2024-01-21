package com.wanted.preonboarding.ticket.exception;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvisor {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception exception) {
        return setResponseFromException(HttpStatus.INTERNAL_SERVER_ERROR, exception);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleDeviceException(EntityNotFoundException exception) {
        return setResponseFromException(HttpStatus.NOT_FOUND, exception);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleDeviceException(MethodArgumentNotValidException exception) {
        String defaultMessage = exception.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return setResponseFromException(HttpStatus.BAD_REQUEST, defaultMessage);
    }

    private <E extends Exception> ResponseEntity<?> setResponseFromException(HttpStatus httpStatus, String errorMessage) {
        log.error("Error: {}", errorMessage);
        return ResponseEntity.status(httpStatus)
                .body(ResponseHandler.<List<ReserveInfo>>builder()
                        .statusCode(httpStatus)
                        .message(errorMessage)
                        .data(null)
                        .build());
    }

    private <E extends Exception> ResponseEntity<?> setResponseFromException(HttpStatus httpStatus, E exception) {
        log.error("Error: {}", exception.getMessage());
        return ResponseEntity.status(httpStatus)
                .body(ResponseHandler.<List<ReserveInfo>>builder()
                        .statusCode(httpStatus)
                        .message(exception.getMessage())
                        .data(null)
                        .build());
    }

}