package com.wanted.preonboarding.ticket.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.ticket.domain.discount_policy.PercentDiscountPolicy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Performance 테스트")
public class PerformanceTest {

	@Test
	public void Performance_calculateFee(){;
		int discountPercent = 10;
		int price = 20000;
		PerformanceCreator performanceCreator = new PerformanceCreator();
		performanceCreator.setPrice(price);
		performanceCreator.setDiscountPolicy(new PercentDiscountPolicy(discountPercent));
		Performance performance = performanceCreator.getPerformance();

		int fee = performance.calculateFee();

		assertThat(fee).isEqualTo(price - (price * discountPercent / 100));
	}

}