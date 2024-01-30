package com.wanted.preonboarding.ticket.application.discount.policy;

import com.wanted.preonboarding.ticket.dto.request.discount.DiscountInfo;

/*
* 할인 정책
* */
public interface DiscountPolicy {
    double getDiscountAmount(DiscountInfo discountInfo, int originPrice);
}
