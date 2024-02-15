package com.wanted.preonboarding.layered.service.discount;

public class DefaultDiscountPolicy implements DiscountPolicy {
  @Override
  public int calc(int money) {
    return money;
  }
}
