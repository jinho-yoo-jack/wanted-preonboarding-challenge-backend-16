package com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.validator;

import com.wanted.preonboarding.performance.domain.entity.Performance;

public interface DiscountValidator {

    boolean isAffecting(final Performance performance);
}
