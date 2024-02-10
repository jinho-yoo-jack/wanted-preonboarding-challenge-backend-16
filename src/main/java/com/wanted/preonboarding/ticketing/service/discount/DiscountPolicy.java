package com.wanted.preonboarding.ticketing.service.discount;

import com.wanted.preonboarding.ticketing.service.dto.Discount;
import com.wanted.preonboarding.ticketing.service.dto.DiscountInfo;


public interface DiscountPolicy {
    Discount calculateDiscount(DiscountInfo discountInfo);
}
