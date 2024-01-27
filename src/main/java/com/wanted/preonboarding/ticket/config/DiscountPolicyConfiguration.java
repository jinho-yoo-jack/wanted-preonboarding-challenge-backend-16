package com.wanted.preonboarding.ticket.config;

import com.wanted.preonboarding.account.domain.vo.Money;
import com.wanted.preonboarding.ticket.application.strategy.DiscountPolicy;
import com.wanted.preonboarding.ticket.application.strategy.FixedDiscountPolicy;
import com.wanted.preonboarding.ticket.application.strategy.RoundDiscountCondition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DiscountPolicyConfiguration {

    @Bean
    public DiscountPolicy discountPolicy() {
        return new FixedDiscountPolicy(
                List.of(new RoundDiscountCondition(List.of(1, 3))),
                Money.createMoney(BigDecimal.valueOf(1000))
        );
    }
}
