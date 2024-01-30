package com.wanted.preonboarding.ticket.domain.discount_policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class NoneDiscountPolicyTest {
	@Test
	void calculateFee_NONE_DISCOUNT_POLICY() {
		//given
		int price = 20000;
		NoneDiscountPolicy discountPolicy = new NoneDiscountPolicy();

		//when
		int discountFee = discountPolicy.getDiscountAmount(price);

		//then
		assertThat(discountFee).isEqualTo(0);
	}

}