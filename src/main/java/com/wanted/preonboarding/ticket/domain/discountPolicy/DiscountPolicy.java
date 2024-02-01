package com.wanted.preonboarding.ticket.domain.discountPolicy;

public interface DiscountPolicy {
	boolean checkAge(int age);

	double discount(int price);
}
