package com.wanted.preonboarding.ticket.application.discount;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import lombok.Builder;

public class CategoryDiscountPolicy extends DiscountPolicy {
    private final PerformanceType performanceType;
    private final Integer discountAmount;

    @Builder
    public CategoryDiscountPolicy(PerformanceType performanceType, Integer discountAmount) {
        this.performanceType = performanceType;
        this.discountAmount = discountAmount;
    }

    protected Integer getDiscountPrice(Integer price, Performance performance) {
        if(performance.getType() == performanceType){
            return (price < discountAmount) ? price : discountAmount;
        }
        return 0;
    }
}
