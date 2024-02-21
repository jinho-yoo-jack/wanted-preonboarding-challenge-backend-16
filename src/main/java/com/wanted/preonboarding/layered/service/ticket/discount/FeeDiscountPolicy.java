package com.wanted.preonboarding.layered.service.ticket.discount;

public class FeeDiscountPolicy implements DiscountPolicy {
  private final Integer discountFee;
  private final Long    price;

  public FeeDiscountPolicy(Integer fee, Long price) {
    this.discountFee = fee;
    this.price = price;
  }

  @Override
  public Long calcFee() {
    return this.price - this.discountFee;
  }
}
