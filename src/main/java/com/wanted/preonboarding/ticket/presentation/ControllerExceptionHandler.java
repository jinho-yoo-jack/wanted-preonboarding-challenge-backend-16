package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.exception.CommonErrorCode;
import com.wanted.preonboarding.core.domain.exception.CustomException;
import com.wanted.preonboarding.core.domain.exception.ErrorCode;
import com.wanted.preonboarding.core.domain.response.ErrorResponseHandler;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponseHandler> handleCustomException(final CustomException e) {
        return createErrorResponseEntity(e.getErrorCode());
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ErrorResponseHandler> handleNoHandlerFoundException(final NoHandlerFoundException e) {
        return createErrorResponseEntity(CommonErrorCode.NO_HANDLER_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseHandler> handleHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException e) {
        return createErrorResponseEntity(CommonErrorCode.METHOD_NOT_SUPPORTED);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseHandler> handleException(final Exception e) {
        e.printStackTrace();
        return createErrorResponseEntity(CommonErrorCode.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseHandler> createErrorResponseEntity(ErrorCode errorCode){
        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(createErrorResponseHandler(errorCode)
                );
    }

    private ErrorResponseHandler createErrorResponseHandler(ErrorCode errorCode){
        return ErrorResponseHandler.builder()
                        .statusCode(errorCode.getHttpStatus())
                        .code(errorCode.name())
                        .message(errorCode.getMessage())
                        .build();
    }
}
