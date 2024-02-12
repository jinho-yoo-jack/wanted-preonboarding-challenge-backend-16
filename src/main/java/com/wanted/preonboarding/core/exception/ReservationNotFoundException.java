package com.wanted.preonboarding.core.exception;

import com.wanted.preonboarding.core.exception.code.ErrorCode;
import org.springframework.http.HttpStatus;

public class ReservationNotFoundException extends WantedTicketException {

    public ReservationNotFoundException() {
        super(HttpStatus.NOT_FOUND, ErrorCode.RESERVATION_NOT_FOUND.getErrorCode(), ErrorCode.RESERVATION_NOT_FOUND.getMessage());
    }

    public ReservationNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, ErrorCode.RESERVATION_NOT_FOUND.getErrorCode(), message);
    }
}
