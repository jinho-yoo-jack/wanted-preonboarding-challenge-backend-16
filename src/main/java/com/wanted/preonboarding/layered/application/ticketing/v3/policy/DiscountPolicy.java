package com.wanted.preonboarding.layered.application.ticketing.v3.policy;

@FunctionalInterface
public interface DiscountPolicy {
    int calculateDiscountFee();
}
