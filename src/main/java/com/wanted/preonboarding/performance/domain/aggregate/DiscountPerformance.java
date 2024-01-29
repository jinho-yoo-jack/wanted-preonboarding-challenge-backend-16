package com.wanted.preonboarding.performance.domain.aggregate;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.domain.model.Discounts;
import lombok.AllArgsConstructor;

@AllArgsConstructor(staticName = "of")
public class DiscountPerformance {

    private final Discounts discounts;
    private final Performance performance;

    public boolean isAffordableByReservation(long account) {
        return getDiscountedPrice() <= account;
    }

    private long getDiscountedPrice() {
        return discounts.calculateDiscountedPrice(performance);
    }
}
