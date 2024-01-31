package com.wanted.preonboarding.performance.domain.valueObject.discount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanted.preonboarding.performance.domain.dto.CreateDiscountRequest;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.calculator.DiscountCalculator;
import com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.calculator.AmountDiscountCalculator;
import com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.calculator.PercentDiscountCalculator;

public enum DiscountCalculatorType {

    AMOUNT,
    PERCENT;

    @JsonValue
    public String getName() {
        return name();
    }

    @JsonCreator
    public static DiscountCalculatorType fromJson(String name){
        return valueOf(name);
    }

    public static DiscountCalculator generateCalculator(final CreateDiscountRequest request) {
        DiscountCalculatorType type = request.getCalculatorType();
        if(type.equals(AMOUNT)) {
            return AmountDiscountCalculator.of(request.getAmount());
        }

        if(type.equals(PERCENT)) {
            return PercentDiscountCalculator.of(request.getPercent());
        }

        throw new Error("할인 계산 타입 명시가 되어있지 않습니다.");
    }
}
