package com.wanted.preonboarding.ticket.application.discount;

import org.springframework.stereotype.Component;

import static com.wanted.preonboarding.core.domain.util.Value.DISCOUNT_TYPE_NO;

@Component
public class NoDiscountService implements DiscountService{
    private final String discountType = DISCOUNT_TYPE_NO;
    @Override
    public int calculatePrice(int price) {
        return 0;
    }

    @Override
    public String getDiscountType() {
        return discountType;
    }
}
