package com.wanted.preonboarding.performance.domain.valueObject.discount.strategy.validator;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.domain.interfaces.discount.strategy.validator.DiscountValidator;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor(staticName = "of")
public class ReserveDateDiscountValidator implements DiscountValidator {

    private final Date startDate;
    private final Date endDate;

    @Override
    public boolean isAffecting(Performance performance) {
        Date now = new Date();
        return (startDate.after(now) || startDate.equals(now)) &&
                (endDate.before(now) || endDate.equals(now));
    }
}
