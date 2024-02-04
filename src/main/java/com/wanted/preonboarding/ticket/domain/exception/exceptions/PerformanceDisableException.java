package com.wanted.preonboarding.ticket.domain.exception.exceptions;

public class PerformanceDisableException extends RuntimeException{
    public PerformanceDisableException() {
        super("해당 공연은 예매 불가 상태입니다.");
    }
}
