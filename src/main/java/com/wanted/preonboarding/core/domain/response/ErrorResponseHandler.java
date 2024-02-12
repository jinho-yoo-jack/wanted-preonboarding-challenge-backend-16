package com.wanted.preonboarding.core.domain.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public record ErrorResponseHandler(HttpStatus statusCode, String code, String message) {
}
