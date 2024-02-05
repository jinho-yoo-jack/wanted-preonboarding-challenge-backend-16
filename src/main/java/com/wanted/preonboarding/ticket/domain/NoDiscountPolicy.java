package com.wanted.preonboarding.ticket.domain;


import org.springframework.stereotype.Component;

@Component
public class NoDiscountPolicy implements DiscountPolicy {
    @Override
    public int calculateDiscountAmount(int price) {
        return price;
    }
}
