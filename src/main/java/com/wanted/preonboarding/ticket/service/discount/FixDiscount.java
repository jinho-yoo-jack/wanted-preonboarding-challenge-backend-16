package com.wanted.preonboarding.ticket.service.discount;

public class FixDiscount implements DiscountPolicy {

    @Override
    public double discount(double amount) {
        if (amount < 10000) {
            return amount;
        }
        return amount - 1000;
    }
}
