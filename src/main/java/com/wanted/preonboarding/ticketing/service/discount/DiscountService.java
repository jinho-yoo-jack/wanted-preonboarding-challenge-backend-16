package com.wanted.preonboarding.ticketing.service.discount;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final List<DiscountPolicy> discountPolicies;

    public int calculateMaximumDiscount(Performance performance) {
        int maxDiscount = 0;

        for (DiscountPolicy discountPolicy : discountPolicies) {
            int discount = discountPolicy.discount(performance);

            if (maxDiscount < discount) {
                maxDiscount = discount;
            }
        }

        return maxDiscount;
    }
}
