package com.wanted.preonboarding.ticket.exception.argument;

public class InvalidSeatException extends InvalidArgumentException {

    private static final String message = "유효하지 않은 좌석 정보입니다.";

    public InvalidSeatException() {
        super(message);
    }
}
