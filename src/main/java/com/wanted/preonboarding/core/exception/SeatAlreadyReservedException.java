package com.wanted.preonboarding.core.exception;

import com.wanted.preonboarding.core.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class SeatAlreadyReservedException extends WantedTicketException {

    public SeatAlreadyReservedException() {
        super(HttpStatus.CONFLICT, ErrorCode.SEAT_ALREADY_RESERVED.getErrorCode(), ErrorCode.SEAT_ALREADY_RESERVED.getMessage());
    }

    public SeatAlreadyReservedException(String message) {
        super(HttpStatus.CONFLICT, ErrorCode.SEAT_ALREADY_RESERVED.getErrorCode(), message);
    }
}
