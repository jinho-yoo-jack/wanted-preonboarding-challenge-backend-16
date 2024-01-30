package com.wanted.preonboarding.ticket.application.discount.policy;

import com.wanted.preonboarding.ticket.dto.request.discount.DiscountInfo;

/*
* 연장자 할인
* */
public class ElderDiscountPolicy implements DiscountPolicy {

    private static final int ELDER_AGE = 65;
    private static final double DISCOUNT_PERCENT = 10;

    public boolean isDiscountSubject(final DiscountInfo discountInfo) {
        return discountInfo.age() >= ELDER_AGE;
    }

    @Override
    public double getDiscountAmount(final DiscountInfo discountInfo, final int originPrice) {
        if (!isDiscountSubject(discountInfo)) return 0;

        double percent = DISCOUNT_PERCENT / 100;
        return originPrice * percent;
    }
}
