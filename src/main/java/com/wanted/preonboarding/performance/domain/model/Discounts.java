package com.wanted.preonboarding.performance.domain.model;

import com.wanted.preonboarding.performance.domain.dto.CreateDiscountRequest;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.domain.valueObject.discount.Discount;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
public class Discounts {

    private final List<Discount> discounts = new ArrayList<>();

    public long calculateDiscountedPrice(final Performance performance) {
        long discountedPrice = performance.getPrice();

        for(Discount discount : discounts) {
            if(discount.isAffecting(performance))
                discountedPrice = discount.getDiscountedPrice(discountedPrice);
        }

        return discountedPrice;
    }

    public void addDiscount(final CreateDiscountRequest createDiscountRequest) {
        discounts.add(DiscountFactory.generate(createDiscountRequest));
    }
}
