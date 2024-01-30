package com.wanted.preonboarding.performance.domain;

import com.wanted.preonboarding.performance.domain.discount_policy.DiscountPolicy;
import com.wanted.preonboarding.performance.domain.discount_policy.NoneDiscountPolicy;
import com.wanted.preonboarding.performance.domain.vo.PerformanceType;

public class PerformanceCreator {
	private int price = 20000;
	private DiscountPolicy discountPolicy = new NoneDiscountPolicy();
	public  Performance getPerformance() {
		return new Performance(
			"아무개",
			price,
			PerformanceType.CONCERT,
			discountPolicy);
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setDiscountPolicy(
		DiscountPolicy discountPolicy) {
		this.discountPolicy = discountPolicy;
	}

	public int getPrice() {
		return price;
	}

	public DiscountPolicy getDiscountPolicy() {
		return discountPolicy;
	}
}
