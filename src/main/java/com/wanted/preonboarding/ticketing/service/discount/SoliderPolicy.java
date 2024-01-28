package com.wanted.preonboarding.ticketing.service.discount;

import com.wanted.preonboarding.ticketing.domain.dto.Discount;
import com.wanted.preonboarding.ticketing.domain.dto.DiscountInfo;
import org.springframework.stereotype.Service;

@Service
public class SoliderPolicy implements DiscountPolicy {
    private static final String DISCOUNT_POLICY_NAME = "Solider";
    private static final int DISCOUNT_PERCENT = 15;

    @Override
    public Discount calculateDiscount(DiscountInfo discountInfo) {
        if (discountInfo.getIsSolider()) {
            int discountMoney = discountInfo.calculateDiscountAmount(DISCOUNT_PERCENT);
            return Discount.from(discountMoney, DISCOUNT_POLICY_NAME);
        }

        return Discount.NoDiscount(DISCOUNT_POLICY_NAME);
    }
}
