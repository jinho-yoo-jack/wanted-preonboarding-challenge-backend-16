package com.wanted.preonboarding.layered.service.ticket.discount;

@FunctionalInterface
public interface DiscountPolicy {
  Long calcFee();
}
