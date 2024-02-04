package com.wanted.preonboarding.ticket.application.discount;

import org.springframework.stereotype.Component;

import static com.wanted.preonboarding.core.domain.util.Value.DISCOUNT_TYPE_VIP;

@Component
public class VipDiscountService implements DiscountService{
    private final String discountType = DISCOUNT_TYPE_VIP;
    double discountRate = 0.3;
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
