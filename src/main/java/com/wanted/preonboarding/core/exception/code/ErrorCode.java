package com.wanted.preonboarding.core.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    PERFORMANCE_NOT_FOUND(40401, "존재하지 않는 공연입니다."),
    PERFORMANCE_SEAT_INFO_NOT_FOUND(40402, "존재하지 않는 좌석입니다."),
    RESERVATION_NOT_FOUND(40403, "예매 내역이 존재하지 않습니다."),
    DISABLE_DISCOUNT(40901, "현재 적용 불가능한 할인입니다."),
    PAYMENT_AMOUNT_NOT_CORRECT(40902, "결제 금액이 정확하지 않습니다."),
    SEAT_ALREADY_RESERVED(40902, "이미 예매된 좌석입니다.")
    ;

    private final int errorCode;
    private final String message;
}
