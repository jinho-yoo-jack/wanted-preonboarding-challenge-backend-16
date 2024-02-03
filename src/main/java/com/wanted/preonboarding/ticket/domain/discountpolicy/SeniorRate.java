package com.wanted.preonboarding.ticket.domain.discountpolicy;

public class SeniorRate implements DiscountPolicy{

	@Override
	public boolean checkAge(int age) {
		return age > 64;
	}

	@Override
	public double discount(int price) {
		return price * 0.3;
	}
}
