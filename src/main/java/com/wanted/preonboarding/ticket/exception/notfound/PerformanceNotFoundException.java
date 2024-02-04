package com.wanted.preonboarding.ticket.exception.notfound;

public class PerformanceNotFoundException extends NotFoundException {

    private static final String message = "공연 & 전시 정보를 찾을 수 없습니다.";

    public PerformanceNotFoundException() {
        super(message);
    }
}
