package com.wanted.preonboarding.ticket.domain.discount;

import com.wanted.preonboarding.ticket.domain.dto.request.DiscountRequest;

public interface DiscountPolicy {
    int discount(DiscountRequest discountRequest);

    String getPolicyName();
}
