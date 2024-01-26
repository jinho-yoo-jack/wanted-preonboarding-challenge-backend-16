package com.wanted.preonboarding.ticketing.service.discount;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;


public interface DiscountPolicy {
    int discount(Performance performance);
}
