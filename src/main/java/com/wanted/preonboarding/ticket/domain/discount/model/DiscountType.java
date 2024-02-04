package com.wanted.preonboarding.ticket.domain.discount.model;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum DiscountType {
    AMOUNT(0, "금액 할인"),
    PERCENT(1, "% 할인");

    private final int index;
    private final String description;

    DiscountType(int index, String description) {
        this.index = index;
        this.description = description;
    }

    public static DiscountType convertToEnum(int index) {
        return Arrays.stream(DiscountType.values())
            .filter(discountType -> discountType.index == index)
            .findFirst()
            .orElseThrow(IllegalAccessError::new);
    }
}
