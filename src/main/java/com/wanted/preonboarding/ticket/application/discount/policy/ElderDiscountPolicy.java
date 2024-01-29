package com.wanted.preonboarding.ticket.application.discount.policy;

import com.wanted.preonboarding.ticket.dto.request.discount.DiscountInfo;

/*
* 연장자 할인
* */
public class ElderDiscountPolicy implements DiscountPolicy {

    @Override
    public boolean isDiscountSubject(DiscountInfo discountInfo) {
        return false;
    }

    @Override
    public double getDiscountAmount(double origin) {
        return 0;
    }
}
