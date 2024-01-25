package com.wanted.preonboarding.util;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BigDecimalUtilsTest {

    @DisplayName("들어오는 문자열이 숫자로 변환 가능하다면 정상적으로 반환한다.")
    @ValueSource(strings = {"0", "1", "10000000", "0.1", "0.01", "1.05"})
    @ParameterizedTest
    void convertToBigDecimalFromString(String value) {
        // given

        // when
        BigDecimal bigDecimal = BigDecimalUtils.convertToBigDecimal(value);

        // then
        assertThat(bigDecimal).isNotNull();
    }

    @DisplayName("들어오는 문자열이 숫자로 변환 불가능하다면 예외를 반환한다.")
    @ValueSource(strings = {"0a", "1b", "bb2", "3.8a"})
    @ParameterizedTest
    void convertToBigDecimalWithException(String value) {
        // given

        // when & then
        assertThatThrownBy(() -> BigDecimalUtils.convertToBigDecimal(value))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(String.format("[%s] 금액 변환에 실패하였습니다.", value));
    }
}