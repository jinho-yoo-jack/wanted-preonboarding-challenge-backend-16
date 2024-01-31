package com.wanted.preonboarding.performance.domain.valueObject.discount;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.calculator.DiscountCalculator;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.validator.DiscountValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Discount {

    private final DiscountValidator validator;
    private final DiscountCalculator calculator;

    public boolean isAffecting(final Performance performance) {
        return validator.isAffecting(performance);
    }

    public long getDiscountedPrice(final long price) {
        return calculator.getDiscountedPrice(price);
    }
}
