package com.wanted.preonboarding.ticket.application.discount;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

public abstract class DiscountPolicy {
    private DiscountPolicy next;

    public static DiscountPolicy link(DiscountPolicy first, DiscountPolicy... chain) {
        DiscountPolicy head = first;
        for (DiscountPolicy nextInChain: chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public final Integer calculateDiscountedPrice(Integer price, Performance performance){
        Integer discountedPrice = price - getDiscountPrice(price, performance);

        if(next == null){
            return discountedPrice;
        }
        return next.calculateDiscountedPrice(discountedPrice, performance);
    }

    protected abstract Integer getDiscountPrice(Integer price, Performance performance);
}
