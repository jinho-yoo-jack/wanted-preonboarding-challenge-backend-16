package com.wanted.preonboarding.ticket.service.discount;

import java.math.BigDecimal;

public interface DiscountPolicy {
    BigDecimal discount(BigDecimal amount);
}
