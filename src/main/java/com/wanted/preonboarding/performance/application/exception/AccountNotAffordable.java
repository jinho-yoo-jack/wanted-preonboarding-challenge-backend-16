package com.wanted.preonboarding.performance.application.exception;

public class AccountNotAffordable extends RuntimeException{

    private static final String MESSAGE = "계봐 잔액 부족으로 결제를 진행할 수 없습니다.";

    public AccountNotAffordable() {
        super(MESSAGE);
    }

    public static String getExceptionMessage() {
        return MESSAGE;
    }
}
