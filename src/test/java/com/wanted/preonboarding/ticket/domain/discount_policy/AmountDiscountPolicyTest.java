package com.wanted.preonboarding.ticket.domain.discount_policy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.domain.PerformanceCreator;
import com.wanted.preonboarding.ticket.domain.vo.PerformanceType;
import org.junit.jupiter.api.Test;

class AmountDiscountPolicyTest {

	@Test
	void calculateFee_AMOUNT_DISCOUNT_POLICY() {
		//given
		int price = 20000;
		int discountAmount = 2000;
		AmountDiscountPolicy discountPolicy = new AmountDiscountPolicy(discountAmount);

		PerformanceCreator performanceCreator = new PerformanceCreator();
		performanceCreator.setPrice(price);
		performanceCreator.setDiscountPolicy(discountPolicy);
		Performance performance = performanceCreator.getPerformance();

		//when
		int fee = performance.calculateFee();

		//then
		assertThat(fee).isEqualTo(price-discountAmount);
	}



}