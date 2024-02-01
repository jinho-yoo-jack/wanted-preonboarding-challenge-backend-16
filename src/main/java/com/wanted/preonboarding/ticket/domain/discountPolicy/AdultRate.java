package com.wanted.preonboarding.ticket.domain.discountPolicy;

public class AdultRate implements DiscountPolicy {

	@Override
	public boolean checkAge(int age) {
		return age < 65 && age > 12;
	}

	@Override
	public double discount(int price) {
		return price;
	}
}
