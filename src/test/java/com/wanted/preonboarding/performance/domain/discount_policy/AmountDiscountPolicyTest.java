package com.wanted.preonboarding.performance.domain.discount_policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("할인 정책: 공연 및 전시 - 금액")
class AmountDiscountPolicyTest {

	@Test
	void calculateFee_AMOUNT_DISCOUNT_POLICY() {
		//given
		int price = 20000;
		int discountAmount = 2000;
		AmountDiscountPolicy discountPolicy = new AmountDiscountPolicy(discountAmount);
		//when
		int discountFee = discountPolicy.getDiscountAmount(price);
		//then
		assertThat(discountFee).isEqualTo(discountAmount);
	}



}