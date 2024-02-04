package com.wanted.preonboarding.ticket.domain.exception.exceptions;

public class ThisSeatDisableException extends RuntimeException{
    public ThisSeatDisableException() {
        super("해당 좌석은 이미 예매가 완료되었습니다.");
    }
}
