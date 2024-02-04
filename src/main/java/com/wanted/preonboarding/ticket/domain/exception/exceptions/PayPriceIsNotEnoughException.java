package com.wanted.preonboarding.ticket.domain.exception.exceptions;

public class PayPriceIsNotEnoughException extends RuntimeException{
    public PayPriceIsNotEnoughException() {
        super("지불하신 금액이 부족합니다.");
    }
}
