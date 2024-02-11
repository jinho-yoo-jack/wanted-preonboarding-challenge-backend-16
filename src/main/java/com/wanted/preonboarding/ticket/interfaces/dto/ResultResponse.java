package com.wanted.preonboarding.ticket.interfaces.dto;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class ResultResponse<T> extends ResponseEntity<T> {//todo 위치

    public ResultResponse(T body) {
        super(body, HttpStatus.OK);
    }

    public ResultResponse(T body, HttpStatusCode status) {
        super(body, status);
    }
}
