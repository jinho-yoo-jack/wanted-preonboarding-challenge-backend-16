package com.wanted.preonboarding.ticket.exception.badrequest;

public class AmountNotEnoughException extends BadRequestException {

    private static final String message = "잔고가 부족합니다.";

    public AmountNotEnoughException() {
        super(message);
    }
}
