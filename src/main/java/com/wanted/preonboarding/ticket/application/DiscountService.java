package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.discount.DiscountPolicy;
import com.wanted.preonboarding.ticket.domain.dto.request.DiscountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class DiscountService {
    private final Set<DiscountPolicy> discountPolicies;

    @Autowired
    public DiscountService(Set<DiscountPolicy> discountPolicies) {
        this.discountPolicies = discountPolicies;
    }

    public int discountPrice(DiscountRequest request) {
        if (discountPolicies.isEmpty()) {
            return request.getPerformancePrice();
        }
        int currentPrice = request.getPerformancePrice();
        for (DiscountPolicy discountPolicy : discountPolicies) {
            int discountPrice = discountPolicy.discount(request);
            if (discountPrice < currentPrice) {
                currentPrice = discountPrice;
            }
        }
        return currentPrice;
    }
}
