package com.wanted.preonboarding.ticket.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ExceptionMessage {

    DISABLED_SEAT("구매 불가능한 좌석입니다."),
    OCCUPIED_SEAT("이미 예약된 좌석입니다."),
    PAYMENT_FAILED("결제에 실패했습니다."),
    NOT_FOUND_PERFORMANCE("공연을 찾을 수 없습니다."),
    NOT_FOUND_SEAT_INFO("좌석을 찾을 수 없습니다.");

    private final String message;
}
