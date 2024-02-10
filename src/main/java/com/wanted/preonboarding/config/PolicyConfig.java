package com.wanted.preonboarding.config;

import com.wanted.preonboarding.ticket.application.policy.discount.DefaultDiscountPolicy;
import com.wanted.preonboarding.ticket.application.policy.discount.DiscountPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PolicyConfig {

	@Bean
	public DiscountPolicy discountPolicy() {
		return new DefaultDiscountPolicy();
	}
}
