package com.wanted.preonboarding.performance.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.wanted.preonboarding.performance.domain.discount_policy.PercentDiscountPolicy;
import com.wanted.preonboarding.uitl.domaincreator.PerformanceCreator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("도메인: 공연 및 전시 - Performance")
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