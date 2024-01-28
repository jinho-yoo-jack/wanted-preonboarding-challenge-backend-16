package com.wanted.preonboarding.ticketing.service.discount;

import com.wanted.preonboarding.ticketing.domain.dto.Discount;
import com.wanted.preonboarding.ticketing.domain.dto.DiscountInfo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class EarlyBirdDiscountPolicy implements DiscountPolicy {
    private static final String DISCOUNT_POLICY_NAME = "Early Bird";
    private static final int DISCOUNT_PERCENT = 10;

    @Override
    public Discount calculateDiscount(DiscountInfo discountInfo) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekLater = now.plusWeeks(1);

        if (discountInfo.isAfter(oneWeekLater)) {
            int discountMoney = discountInfo.calculateDiscountAmount(DISCOUNT_PERCENT);

            return Discount.from(discountMoney, DISCOUNT_POLICY_NAME);
        }

        return Discount.NoDiscount();
    }
}
