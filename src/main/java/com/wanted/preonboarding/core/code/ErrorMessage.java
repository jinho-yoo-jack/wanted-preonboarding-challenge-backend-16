package com.wanted.preonboarding.core.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorMessage {
    RESERVATION_FAIL("해당 공연은 예약이 불가능 합니다."),
    OVER_PRICE("결제 가능 금액보다 공연/전시 금액 비쌉니다."),
    EMPTY_DATA("예약 데이터가 없습니다."),
    CANCEL_STATUS_APPLY("신청 상태의 예약만 취소 가능합니다."),


    ;

    private final String description;
}
