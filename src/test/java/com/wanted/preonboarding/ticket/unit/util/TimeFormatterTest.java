package com.wanted.preonboarding.ticket.unit.util;

import com.wanted.preonboarding.ticket.application.common.util.TimeFormatter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TimeFormatterTest {

    @Test
    @DisplayName("LocalDateTime을 사람이 읽기 쉬운 형태로 변환")
    void shouldConvertToReadableFormat() {
        // Given
        LocalDateTime localDateTime = LocalDateTime.of(2021, 7, 1, 10, 0);
        String expected = "2021년 07월 01일 10시 00분";

        // When
        String actual = TimeFormatter.convertToReadableFormat(localDateTime);

        // Then
        assertThat(actual).isEqualTo(expected);
    }
}
