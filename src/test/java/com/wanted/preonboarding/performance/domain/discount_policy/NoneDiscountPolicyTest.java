package com.wanted.preonboarding.performance.domain.discount_policy;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
@DisplayName("할인 정책: 공연 및 전시 - 무옵션")

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