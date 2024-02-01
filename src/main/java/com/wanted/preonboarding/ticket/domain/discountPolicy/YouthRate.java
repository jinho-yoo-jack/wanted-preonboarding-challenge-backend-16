package com.wanted.preonboarding.ticket.domain.discountPolicy;

public class YouthRate implements DiscountPolicy{
	@Override
	public boolean checkAge(int age) {
		return age < 13 && age > 0;
	}

	@Override
	public double discount(int price) {
		return price * 0.5;
	}
}
