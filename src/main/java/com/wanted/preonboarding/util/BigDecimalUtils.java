package com.wanted.preonboarding.util;

import java.math.BigDecimal;

public final class BigDecimalUtils {

    private static final String MESSAGE_FORMAT = "[%s] 금액 변환에 실패하였습니다.";

    private BigDecimalUtils() {

    }

    public static BigDecimal convertToBigDecimal(String value) {
        try {
            return new BigDecimal(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(String.format(MESSAGE_FORMAT, value), e);
        }
    }
}
