package com.wanted.preonboarding.ticketing.domain.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Discount {
    private static final int DISCOUNT_NONE = 0;

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

    public static Discount NoDiscount(String discountPolicyName) {
        return Discount.builder()
                .discountPolicyName(discountPolicyName)
                .change(DISCOUNT_NONE)
                .isApplied(false)
                .build();
    }

    public boolean isHigherThan(Discount discount) {
        return this.change < discount.change;
    }
}
