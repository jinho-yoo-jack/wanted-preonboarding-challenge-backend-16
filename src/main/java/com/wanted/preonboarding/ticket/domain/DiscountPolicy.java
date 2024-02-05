package com.wanted.preonboarding.ticket.domain;

public interface DiscountPolicy {
    int calculateDiscountAmount(int amount);
}
