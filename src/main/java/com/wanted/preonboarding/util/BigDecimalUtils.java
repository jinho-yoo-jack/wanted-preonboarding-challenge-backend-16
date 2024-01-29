package com.wanted.preonboarding.util;

import java.math.BigDecimal;

public interface BigDecimalUtils {

    String MESSAGE_FORMAT = "[%s] 금액 변환에 실패하였습니다.";

    static BigDecimal convertToBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format(MESSAGE_FORMAT, value), e);
        }
    }
}
