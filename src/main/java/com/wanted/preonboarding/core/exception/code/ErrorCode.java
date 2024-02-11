package com.wanted.preonboarding.core.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    PERFORMANCE_NOT_FOUND(40401, "존재하지 않는 공연입니다."),
    PERFORMANCE_SEAT_INFO_NOT_FOUND(40402, "존재하지 않는 좌석입니다."),
    RESERVATION_NOT_FOUND(40403, "예매 내역이 존재하지 않습니다.")
    ;

    private final int errorCode;
    private final String message;
}
