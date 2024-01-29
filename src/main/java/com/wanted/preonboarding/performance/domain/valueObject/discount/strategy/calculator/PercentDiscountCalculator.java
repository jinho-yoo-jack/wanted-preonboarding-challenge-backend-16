package com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.calculator;

import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.calculator.DiscountCalculator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PercentDiscountCalculator implements DiscountCalculator {

    private final float percent;

    @Override
    public long getDiscountedPrice(long price) {
        return (long) Math.floor(price * percent);
    }
}
