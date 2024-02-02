package com.wanted.preonboarding.uitl.domaincreator;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.discount_policy.DiscountPolicy;
import com.wanted.preonboarding.performance.domain.discount_policy.NoneDiscountPolicy;
import com.wanted.preonboarding.performance.domain.vo.PerformanceType;
import com.wanted.preonboarding.uitl.testdata.TestPerformance;
import com.wanted.preonboarding.uitl.testdata.TestUser;

public class PerformanceCreator {
	private TestUser testUser = new TestUser();
	private TestPerformance testPerformance = new TestPerformance();
	private int price = testPerformance.getPrice();
	private DiscountPolicy discountPolicy = new NoneDiscountPolicy();
	public Performance getPerformance() {
		return Performance.create(
			testUser.getName(),
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
