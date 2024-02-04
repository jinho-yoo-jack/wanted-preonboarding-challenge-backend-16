package com.wanted.preonboarding.ticket.exception.notfound;

public class ReservationNotFoundException extends NotFoundException {

    private static final String message = "예약 정보를 찾을 수 없습니다.";

    public ReservationNotFoundException() {
        super(message);
    }
}
