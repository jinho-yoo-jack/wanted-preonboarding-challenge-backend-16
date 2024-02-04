package com.wanted.preonboarding.ticket.application.discount;

import org.springframework.stereotype.Component;

import static com.wanted.preonboarding.core.domain.util.Value.DISCOUNT_TYPE_WOORI;

@Component
public class WooriDiscountService implements DiscountService{
    private final String discountType = DISCOUNT_TYPE_WOORI;
    double discountRate = 0.1;
    @Override
    public int calculatePrice(int price)
    {
        double discountedPrice = price - (price * discountRate);
        return (int) Math.round(discountedPrice);
    }

    @Override
    public String getDiscountType() {
        return discountType;
    }
}
