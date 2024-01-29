package com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.validator;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.validator.DiscountValidator;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor(staticName = "of")
public class PerformDateDiscountValidator implements DiscountValidator {

    private final Date startDate;
    private final Date endDate;

    @Override
    public boolean isAffecting(Performance performance) {
        Date performDate = performance.getStartDate();
        return startDate.after(performDate) && endDate.before(performDate);
    }
}
