package com.wanted.preonboarding.ticket.exception;

import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Builder
@Getter
public class ExceptionResponse {
    HttpStatus httpStatus;
    String exceptionMessage;

}
