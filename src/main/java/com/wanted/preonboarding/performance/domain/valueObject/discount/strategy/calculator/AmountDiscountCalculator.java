package com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.calculator;

import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.calculator.DiscountCalculator;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class AmountDiscountCalculator implements DiscountCalculator {

    private final long discountAmount;

    @Override
    public long getDiscountedPrice(final long price) {
        long calculated = price - discountAmount;
        return Math.max(calculated, 0);
    }
}
