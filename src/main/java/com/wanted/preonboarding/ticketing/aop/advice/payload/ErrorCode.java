package com.wanted.preonboarding.ticketing.aop.advice.payload;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

import java.time.LocalDateTime;

@Getter
public enum ErrorCode {
    NOT_ENOUGH_BALANCE(400, "C001", "결제 금액이 부족합니다."),
    NOT_FOUND_RESERVATION(500, "C002", "취소할 예약이 존재하지 않습니다."),
    REQUEST_HAS_NULL(400, "C003", "요청에 null은 입력할 수 없습니다."),
    NOT_FOUND_RESERVATIONS(500, "C004", "예약 정보가 없습니다."),
    NOT_FOUND_PERFORMANCE(500,"C005" , "공연 정보가 존재하지 않습니다."),
    NOT_FOUND_PERFORMANCE_SEAT_INFO(500, "C006", "좌석 정보가 존재하지 않습니다."),
    NOT_VALIDATED_PARAM(400, "C007", "파라미터가 유효하지 않습니다."),
    FAILED_SEND_EMAIL(500, "C008", "이메일을 보내는데 실패하였습니다."),
    SOLD_OUT_TICKET(500, "C009", "좌석이 매진되었습니다.");

    private final int status;
    private final String code;
    private final String message;

    ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public ErrorResponse toErrorResponse() {
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(this.status)
                .code(this.code)
                .message(this.message)
                .build();
    }

    public HttpStatusCode toHttpStatus() {
        return HttpStatusCode.valueOf(this.status);
    }
}
