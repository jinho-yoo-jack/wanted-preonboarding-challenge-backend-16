package com.wanted.preonboarding.performance.domain.valueObject.discount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.wanted.preonboarding.performance.domain.dto.CreateDiscountRequest;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.validator.DiscountValidator;
import com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.validator.DistinctDiscountValidator;
import com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.validator.PerformDateDiscountValidator;
import com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.validator.ReserveDateDiscountValidator;
import com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.validator.TypeDiscountValidator;

public enum DiscountValidatorType {

    DISTINCT,
    PERFORMANCE_DATE,
    RESERVE_DATE,
    TYPE;

    @JsonValue
    public String getName() {
        return name();
    }

    @JsonCreator
    public static DiscountValidatorType fromJson(String name){
        return valueOf(name);
    }

    public static DiscountValidator generateValidator(final CreateDiscountRequest request) {
        DiscountValidatorType type = request.getValidatorType();
        if(DISTINCT.equals(type)) {
            return DistinctDiscountValidator.of(request.getPerformanceId());
        }

        if(PERFORMANCE_DATE.equals(type)) {
            return PerformDateDiscountValidator.of(request.getStartDate(), request.getEndDate());
        }

        if(RESERVE_DATE.equals(type)) {
            return ReserveDateDiscountValidator.of(request.getStartDate(), request.getEndDate());
        }

        if(TYPE.equals(type)) {
            return TypeDiscountValidator.of(request.getType());
        }

        throw new Error("할인 계산 타입 명시가 되어있지 않습니다.");
    }
}
