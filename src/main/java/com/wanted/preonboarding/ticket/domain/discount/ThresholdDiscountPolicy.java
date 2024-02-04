package com.wanted.preonboarding.ticket.domain.discount;

import com.wanted.preonboarding.ticket.domain.dto.request.DiscountRequest;
import org.springframework.stereotype.Component;

@Component
public class ThresholdDiscountPolicy implements DiscountPolicy {
    private static final String POLICY_NAME = "특정 금액 이상의 공연 가격 할인";
    private static final int THRESHOLD = 50000;
    private static final double DISCOUNT_RATE = 10;   // 10이면 10%

    @Override
    public int discount(DiscountRequest request) {
        int price = request.getPerformancePrice();
        if (THRESHOLD <= price) {
            return calculateDiscountPrice(request);
        } else {
            return price;
        }
    }

    @Override
    public String getPolicyName() {
        return POLICY_NAME;
    }

    private static int calculateDiscountPrice(DiscountRequest request) {
        double discountPrice = request.getPerformancePrice() * ((100 - DISCOUNT_RATE) / 100);
        return (int) discountPrice;
    }

}
