package com.wanted.preonboarding.ticket.domain.dto.discount;

public interface DiscountPolicy {
    int getDiscountPolicy(int charge, String membershipGrade);
}