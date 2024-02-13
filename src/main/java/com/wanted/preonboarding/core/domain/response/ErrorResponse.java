package com.wanted.preonboarding.core.domain.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@Builder
public class ErrorResponse {
    private String message;
    private int statusCode;
    private HttpStatus status;
}
