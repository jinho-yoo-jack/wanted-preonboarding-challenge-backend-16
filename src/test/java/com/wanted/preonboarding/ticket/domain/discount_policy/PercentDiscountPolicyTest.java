package com.wanted.preonboarding.ticket.domain.discount_policy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.domain.PerformanceCreator;
import org.junit.jupiter.api.Test;

class PercentDiscountPolicyTest {

	@Test
	void calculateFee_PERCENT_DISCOUNT_POLICY() {
		//given
		int price = 20000;
		int discountPercent = 10;
		PercentDiscountPolicy discountPolicy = new PercentDiscountPolicy(discountPercent);
		PerformanceCreator performanceCreator = new PerformanceCreator();
		performanceCreator.setPrice(price);
		performanceCreator.setDiscountPolicy(discountPolicy);

		Performance performance = performanceCreator.getPerformance();

		//when
		int fee = performance.calculateFee();

		//then
		assertThat(fee).isEqualTo(price - (price * discountPercent / 100));
	}

}