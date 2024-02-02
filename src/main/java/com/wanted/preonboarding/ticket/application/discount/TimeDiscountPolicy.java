package com.wanted.preonboarding.ticket.application.discount;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Builder;

import java.time.LocalTime;

public class TimeDiscountPolicy extends DiscountPolicy {
    private final Integer startTime;
    private final Integer endTime;
    private final Integer discountPercent;

    @Builder
    public TimeDiscountPolicy(Integer startTime, Integer endTime, Integer discountPercent) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.discountPercent = discountPercent;
    }

    protected Integer getDiscountPrice(Integer price, Performance performance) {
        Integer currentTime = LocalTime.now().getHour();
        if(startTime <= currentTime && currentTime < endTime){
            return price * discountPercent / 100;
        }
        return 0;
    }
}
