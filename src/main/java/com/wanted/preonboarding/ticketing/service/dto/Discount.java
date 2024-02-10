package com.wanted.preonboarding.ticketing.service.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Discount {
    private static final int DISCOUNT_NONE = 0;
    private static final String DISCOUNT_POLICY_NAME_NONE = "적용된 할인이 없습니다.";

    private String discountPolicyName;
    private int change;
    private boolean isApplied;

    public static Discount from(int discountMoney, String discountPolicyName) {
        return Discount.builder()
                .discountPolicyName(discountPolicyName)
                .change(discountMoney)
                .isApplied(true)
                .build();
    }

    public static Discount NoDiscount() {
        return Discount.builder()
                .discountPolicyName(DISCOUNT_POLICY_NAME_NONE)
                .change(DISCOUNT_NONE)
                .isApplied(false)
                .build();
    }
}
