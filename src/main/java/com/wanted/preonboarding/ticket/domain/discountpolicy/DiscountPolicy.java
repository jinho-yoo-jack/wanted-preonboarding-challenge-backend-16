package com.wanted.preonboarding.ticket.domain.discountpolicy;

public interface DiscountPolicy {
	boolean checkAge(int age);

	double discount(int price);
}
