package com.wanted.preonboarding.ticket.application.discount;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DiscountPolicyConfig {

    @Bean
    public DiscountPolicy discountPolicyBean(){
        DiscountPolicy timeDiscountPolicy =  TimeDiscountPolicy.builder()
                .startTime(0)
                .endTime(1)
                .discountPercent(20)
                .build();
        DiscountPolicy categoryConcertDiscountPolicy = CategoryDiscountPolicy.builder()
                .performanceType(PerformanceType.CONCERT)
                .discountAmount(10000)
                .build();
        DiscountPolicy categoryNoneDiscountPolicy = CategoryDiscountPolicy.builder()
                .performanceType(PerformanceType.NONE)
                .discountAmount(5000)
                .build();

        return DiscountPolicy.link(timeDiscountPolicy, categoryConcertDiscountPolicy, categoryNoneDiscountPolicy);
    }
}
