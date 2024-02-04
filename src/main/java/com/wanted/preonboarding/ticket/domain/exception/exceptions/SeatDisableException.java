package com.wanted.preonboarding.ticket.domain.exception.exceptions;

public class SeatDisableException extends RuntimeException{
    public SeatDisableException() {
        super("해당 좌석은 예매 불가 상태입니다.");
    }
}
