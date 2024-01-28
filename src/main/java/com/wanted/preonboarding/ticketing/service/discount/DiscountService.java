package com.wanted.preonboarding.ticketing.service.discount;

import com.wanted.preonboarding.ticketing.domain.dto.Discount;
import com.wanted.preonboarding.ticketing.domain.dto.DiscountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final List<DiscountPolicy> discountPolicies;

    public Discount calculateMaximumDiscount(DiscountInfo discountInfo) {
        Discount maxDiscount = new Discount();

        for (DiscountPolicy discountPolicy : discountPolicies) {
            Discount discount = discountPolicy.calculateDiscount(discountInfo);

            if (maxDiscount.isHigherThan(discount)) {
                maxDiscount = discount;
            }
        }

        return maxDiscount;
    }
}
