package com.wanted.preonboarding.account.domain.exception;

public class NotEnoughMoneyException extends IllegalStateException {

    public NotEnoughMoneyException(String s) {
        super(s);
    }
}
