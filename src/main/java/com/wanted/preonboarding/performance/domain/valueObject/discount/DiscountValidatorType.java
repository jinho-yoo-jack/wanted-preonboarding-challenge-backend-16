package com.wanted.preonboarding.performance.domain.valueObject.discount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DiscountValidatorType {

    AMOUNT,
    PERCENT;

    @JsonValue
    public String getName() {
        return name();
    }

    @JsonCreator
    public static DiscountValidatorType fromJson(String name){
        return valueOf(name);
    }
}
