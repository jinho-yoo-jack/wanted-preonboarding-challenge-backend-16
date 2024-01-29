package com.wanted.preonboarding.performance.domain.valueObject.discount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DiscountCalculatorType {

    DISTINCT,
    PERFORMANCE_DATE,
    RESERVE_DATE,
    TYPE;

    @JsonValue
    public String getName() {
        return name();
    }

    @JsonCreator
    public static DiscountCalculatorType fromJson(String name){
        return valueOf(name);
    }
}
