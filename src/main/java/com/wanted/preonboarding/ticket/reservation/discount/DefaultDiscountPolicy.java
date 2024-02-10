package com.wanted.preonboarding.ticket.reservation.discount;

public class DefaultDiscountPolicy implements DiscountPolicy {
  @Override
  public int calc(int money) {
    return money;
  }
}
