package com.wanted.preonboarding.ticket.dto.request.discount;

import com.wanted.preonboarding.ticket.domain.discount.Discount;
import com.wanted.preonboarding.ticket.domain.performance.Performance;

public record DiscountInfo(
    Discount discount,
    Performance performance,
    String phoneNumber,
    String name,
    int age
) {

}
