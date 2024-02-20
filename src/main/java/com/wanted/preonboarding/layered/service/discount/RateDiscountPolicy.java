package com.wanted.preonboarding.layered.service.discount;

import java.math.BigDecimal;

public class RateDiscountPolicy implements DiscountPolicy {
  private final BigDecimal  discountRate;
  private final Long        price;

  public RateDiscountPolicy(BigDecimal rate, Long price) {
    this.discountRate = rate;
    this.price = price;
  }

  @Override
  public Long calcFee() {
    return this.price - discountRate.multiply(new BigDecimal(this.price)).longValue();
  }
}
