package com.wanted.preonboarding.ticket.domain.exception.exceptions;

public class PerformanceNotFoundException extends RuntimeException{
    public PerformanceNotFoundException() {
        super("해당 공연을 찾을 수 없습니다.");
    }
}

