package com.wanted.preonboarding.core.domain.response;

import org.springframework.http.HttpStatus;

import lombok.Builder;

@Builder
public record ResponseHandler<T>(HttpStatus statusCode, String message, T data) {
}
