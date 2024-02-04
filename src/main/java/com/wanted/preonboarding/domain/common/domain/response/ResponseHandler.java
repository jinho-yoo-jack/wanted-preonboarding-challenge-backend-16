package com.wanted.preonboarding.domain.common.domain.response;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
public record ResponseHandler<T>(HttpStatus statusCode, String message, T data) {
}
