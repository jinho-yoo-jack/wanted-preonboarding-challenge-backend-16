package com.wanted.preonboarding.ticket.domain.discountPolicy;

import java.util.ArrayList;
import java.util.List;

public class DiscountPolicies {
	private final List<DiscountPolicy> discountPolicies = new ArrayList<>() {
		{
			add(new AdultRate());
			add(new SeniorRate());
			add(new YouthRate());
		}
	};

	public double calculateRate(int age, int price) {
		return findByAge(age).discount(price);
	}

	private DiscountPolicy findByAge(int age) {
		return discountPolicies.stream()
			.filter(discountPolicy -> discountPolicy.checkAge(age))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("유효하지 않은 나이 입니다."));
	}
}
