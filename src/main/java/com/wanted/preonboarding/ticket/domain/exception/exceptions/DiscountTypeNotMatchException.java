package com.wanted.preonboarding.ticket.domain.exception.exceptions;

public class DiscountTypeNotMatchException extends RuntimeException{
    public DiscountTypeNotMatchException() {
        super("요청하신 할인 정책을 찾을 수 없습니다.");
    }
}
