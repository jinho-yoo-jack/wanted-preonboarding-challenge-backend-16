package com.wanted.preonboarding.ticketing.service.discount;

import com.wanted.preonboarding.ticketing.domain.dto.Discount;
import com.wanted.preonboarding.ticketing.domain.dto.DiscountInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final List<DiscountPolicy> discountPolicies;

    public Discount calculateMaximumDiscount(DiscountInfo discountInfo) {
        return discountPolicies.stream()
                .map(discountPolicy -> discountPolicy.calculateDiscount(discountInfo))
                .max(Comparator.comparing(Discount::getChange))
                .orElse(Discount.NoDiscount());
    }
}
