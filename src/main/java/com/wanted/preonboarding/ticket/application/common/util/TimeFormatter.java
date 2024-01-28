package com.wanted.preonboarding.ticket.application.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeFormatter {

    private TimeFormatter() {
        throw new IllegalStateException("유틸리티 클래스는 인스턴스화할 수 없습니다.");
    }

    /**
     * LocalDateTime을 사람이 읽기 쉬운 형태로 변환합니다.
     * <p>
     * e.g. 2021-07-01T10:00 -> 2021년 07월 01일 10시 00분
     */
    public static String convertToReadableFormat(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일 HH시 mm분"));
    }
}
