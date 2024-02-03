package com.wanted.preonboarding.ticket.domain.discount;

import com.wanted.preonboarding.ticket.domain.dto.request.DiscountRequest;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class PreorderDiscountPolicy implements DiscountPolicy {
    private static final String POLICY_NAME = "사전 예약 할인";

    private static final int EARLY_DATE_THRESHOLD = 7;
    private static final double DISCOUNT_RATE = 20;

    @Override
    public String getPolicyName() {
        return POLICY_NAME;
    }

    @Override
    public int discount(DiscountRequest request) {
        long days = calculateDays(request);
        if (days > EARLY_DATE_THRESHOLD) {
            return calculateDiscountPrice(request);
        } else {
            return request.getPerformancePrice();
        }
    }

    private static int calculateDiscountPrice(DiscountRequest request) {
        double discountPrice = request.getPerformancePrice() * ((100 - DISCOUNT_RATE) / 100);
        return (int) discountPrice;
    }

    private static long calculateDays(DiscountRequest request) {
        Duration between = Duration.between(request.getReserveDate(), request.getPerformanceDateTime());
        return between.toDays();
    }

}
