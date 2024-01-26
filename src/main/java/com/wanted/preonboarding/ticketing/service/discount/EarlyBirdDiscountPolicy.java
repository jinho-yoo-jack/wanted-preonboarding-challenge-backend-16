package com.wanted.preonboarding.ticketing.service.discount;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EarlyBirdDiscountPolicy implements DiscountPolicy {
    private static final int DISCOUNT_PERCENT = 10;
    private static final int DISCOUNT_NONE = 0;

    @Override
    public int discount(Performance performance) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekLater = now.plusWeeks(1);

        if (performance.isAfter(oneWeekLater)) {
            return performance.discountEarlyBird(DISCOUNT_PERCENT);
        }

        return DISCOUNT_NONE;
    }
}
