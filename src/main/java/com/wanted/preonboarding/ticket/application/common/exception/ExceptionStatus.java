package com.wanted.preonboarding.ticket.application.common.exception;

import lombok.Getter;

@Getter
public enum ExceptionStatus {

    SEAT_DISABLED(400, "예매할 수 없는 좌석입니다."),
    PERFORMANCE_DISABLED(400, "예매할 수 없는 공연입니다."),
    ARGUMENT_NOT_VALID(400, "요청 값이 올바르지 않습니다."),
    PAYMENT_FAILED_INSUFFICIENT_BALANCE(400, "잔액이 부족합니다."),

    NOT_FOUND_INFO(404, "해당 정보를 찾을 수 없습니다."),

    SEAT_ALREADY_OCCUPIED(409, "이미 예약된 좌석입니다."),

    FAIL_TO_SEND_EMAIL(500, "이메일 전송에 실패했습니다.");

    private final Integer statusCode;
    private final String message;

    ExceptionStatus(Integer statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

}
