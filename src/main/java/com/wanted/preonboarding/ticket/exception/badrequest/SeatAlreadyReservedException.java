package com.wanted.preonboarding.ticket.exception.badrequest;

public class SeatAlreadyReservedException extends BadRequestException {

    private static final String message = "이미 예약된 좌석입니다.";

    public SeatAlreadyReservedException() {
        super(message);
    }
}
