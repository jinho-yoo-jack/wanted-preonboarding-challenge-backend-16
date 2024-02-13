package com.wanted.preonboarding.layered.application.ticketing.v3.policy;

import java.math.BigDecimal;

public class TelecomeDiscountPolicy implements DiscountPolicy {
    private final BigDecimal discountRate;
    private final int fixedPrice;

    public TelecomeDiscountPolicy(BigDecimal rate, int price) {
        discountRate = rate;
        fixedPrice = price;
    }

    @Override
    public int calculateDiscountFee() {
        return fixedPrice - discountRate.multiply(new BigDecimal(String.valueOf(fixedPrice))).intValue();

    }
}
