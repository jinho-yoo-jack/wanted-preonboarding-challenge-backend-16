package com.wanted.preonboarding.ticket.domain.exception;

import com.wanted.preonboarding.core.domain.exception.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TicketErrorCode implements ErrorCode {
    PERFORMANCE_NOT_FOUND(HttpStatus.NOT_FOUND, "공연이 존재하지 않습니다."),
    PERFORMANCE_SEAT_NOT_FOUND(HttpStatus.NOT_FOUND, "좌석이 존재하지 않습니다."),
    RESERVATION_NOT_FOUND(HttpStatus.NOT_FOUND, "예약이 존재하지 않습니다."),
    SUBSCRIPTION_NOT_FOUND(HttpStatus.NOT_FOUND, "구독이 존재하지 않습니다."),

    PERFORMANCE_NOT_RESERVABLE(HttpStatus.BAD_REQUEST, "예약할 수 없는 공연입니다."),
    PERFORMANCE_SEAT_NOT_RESERVABLE(HttpStatus.BAD_REQUEST, "예약할 수 없는 좌석입니다."),
    BALANCE_INSUFFICIENT(HttpStatus.BAD_REQUEST, "잔액이 부족합니다."),

    NOT_RESERVATION_OWNER(HttpStatus.FORBIDDEN, "예약 취소 권한이 없습니다."),
    NOT_SUBSCRIPTION_OWNER(HttpStatus.FORBIDDEN, "구독 취소 권한이 없습니다."),

    DUPLICATED_PERFORMANCE_SEAT(HttpStatus.CONFLICT, "중복된 좌석이 존재합니다."),
    DUPLICATED_SUBSCRIPTION(HttpStatus.CONFLICT, "이미 구독하고 있는 공연입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String message;
}