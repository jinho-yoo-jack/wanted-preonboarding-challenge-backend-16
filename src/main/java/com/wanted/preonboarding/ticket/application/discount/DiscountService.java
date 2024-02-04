package com.wanted.preonboarding.ticket.application.discount;

public interface DiscountService {
    int calculatePrice(int price);
    String getDiscountType();
}
