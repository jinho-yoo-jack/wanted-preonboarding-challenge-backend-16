package com.wanted.preonboarding.ticket.config;

import com.wanted.preonboarding.ticket.application.discount.DefaultDiscountManager;
import com.wanted.preonboarding.ticket.application.discount.DiscountManager;
import com.wanted.preonboarding.ticket.application.discount.policy.DiscountPolicy;
import com.wanted.preonboarding.ticket.application.discount.policy.ElderDiscountPolicy;
import com.wanted.preonboarding.ticket.application.discount.policy.PerformanceDiscountPolicy;
import com.wanted.preonboarding.ticket.domain.discount.DiscountRepository;
import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@Configuration
public class DiscountBeanConfig {

    @DependsOn({"performanceDiscountPolicy", "elderDiscountPolicy"})
    @Bean
    public DiscountManager discountManager(DiscountRepository discountRepository) {
        List<DiscountPolicy> discountPolicies = List.of(
            performanceDiscountPolicy(discountRepository),
            elderDiscountPolicy()
        );

        return new DefaultDiscountManager(discountPolicies);
    }

    @Bean
    public DiscountPolicy performanceDiscountPolicy(DiscountRepository discountRepository) {
        return new PerformanceDiscountPolicy(discountRepository);
    }

    @Bean
    public DiscountPolicy elderDiscountPolicy() {
        return new ElderDiscountPolicy();
    }

}
