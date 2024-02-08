package com.wanted.preonboarding.ticket.service.discount;

import java.math.BigDecimal;

public class FixDiscount implements DiscountPolicy {

    // 1만원 부터 크면 1천원 할인
    @Override
    public BigDecimal discount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(10000)) < 0)  {
            return amount;
        }
        return amount.add(BigDecimal.valueOf(-1000));
    }
}
