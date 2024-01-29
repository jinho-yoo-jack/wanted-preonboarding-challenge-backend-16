package com.wanted.preonboarding.performance.domain.model;

import com.wanted.preonboarding.performance.domain.dto.CreateDiscountRequest;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.calculator.DiscountCalculator;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.validator.DiscountValidator;
import com.wanted.preonboarding.performance.domain.valueObject.discount.Discount;
import com.wanted.preonboarding.performance.domain.valueObject.discount.DiscountCalculatorType;
import com.wanted.preonboarding.performance.domain.valueObject.discount.DiscountValidatorType;

public class DiscountFactory {

    public static Discount generate(final CreateDiscountRequest request) {
        DiscountCalculator calculator = DiscountCalculatorType.generateCalculator(request);
        DiscountValidator validator = DiscountValidatorType.generateValidator(request);
        return new Discount(validator, calculator);
    }
}
