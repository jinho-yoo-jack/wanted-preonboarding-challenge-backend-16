package com.wanted.preonboarding.core.domain.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
public record ResponseHandler<T>(HttpStatus statusCode, String message, T data) {
    public static final String MESSAGE_SUCCESS = "success";
    public static final String MESSAGE_FAIL = "fail";

    public static <T> ResponseEntity<ResponseHandler<T>> createResponse(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status)
                .body(
                        ResponseHandler.<T>builder()
                                .statusCode(status)
                                .message(message)
                                .data(data)
                                .build()
                );
    }

}
