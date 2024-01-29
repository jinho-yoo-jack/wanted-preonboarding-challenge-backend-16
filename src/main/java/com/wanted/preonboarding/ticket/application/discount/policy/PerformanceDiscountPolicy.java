package com.wanted.preonboarding.ticket.application.discount.policy;

import com.wanted.preonboarding.ticket.domain.discount.DiscountRepository;
import com.wanted.preonboarding.ticket.dto.request.discount.DiscountInfo;
import lombok.RequiredArgsConstructor;

/*
* 공연에 적용된 할인 (DISCOUNT 테이블 조회)
* */
@RequiredArgsConstructor
public class PerformanceDiscountPolicy implements DiscountPolicy {

    private final DiscountRepository discountRepository;

    @Override
    public boolean isDiscountSubject(DiscountInfo discountInfo) {
        return false;
    }

    @Override
    public double getDiscountAmount(double origin) {
        return 0;
    }
}
