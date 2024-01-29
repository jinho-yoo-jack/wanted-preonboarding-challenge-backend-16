package com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.validator;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.validator.DiscountValidator;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class DistinctDiscountValidator implements DiscountValidator {

    private final UUID performanceId;

    @Override
    public boolean isAffecting(Performance performance) {
        return this.performanceId.equals(performance.getId());
    }
}
