package com.wanted.preonboarding.ticket.exception;

import lombok.Builder;

@Builder
public record ExceptionResponse(int statusCode, String message) {
}
