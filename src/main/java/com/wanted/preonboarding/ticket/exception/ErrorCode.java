package com.wanted.preonboarding.ticket.exception;

import lombok.Getter;

@Getter
public enum ErrorCode {
    NO_AVAILABLE_CANCEL_SEAT("좌석을 취소할 수 없습니다 (예매 되어있지 않는 좌석)"),
    PERFORMANCE_NOT_FOUND("공연 정보가 존재하지 않습니다."),
    PERFORMANCE_SEAT_INFO_NOT_FOUND("좌석 정보가 존재하지 않습니다."),
    RESERVATION_NOT_FOUND("예매된 공연이 없습니다."),
    SEAT_ALREADY_RESERVED("좌석이 매진되었습니다."),
    NOT_ENOUGH_BALANCE("잔고가 부족합니다.");


    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
