package com.wanted.preonboarding.layered.service.discount;

@FunctionalInterface
public interface DiscountPolicy {
  Long calcFee();
}
