package com.wanted.preonboarding.ticketing.service.discount;

import com.wanted.preonboarding.ticketing.domain.dto.Discount;
import com.wanted.preonboarding.ticketing.domain.dto.DiscountInfo;


public interface DiscountPolicy {
    Discount calculateDiscount(DiscountInfo performance);
}
