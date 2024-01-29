package com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.validator;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.validator.DiscountValidator;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class TypeDiscountValidator implements DiscountValidator {

    private final int type;

    @Override
    public boolean isAffecting(Performance performance) {
        return performance.getType() == type;
    }
}
