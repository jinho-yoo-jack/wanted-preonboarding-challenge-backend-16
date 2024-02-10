package com.wanted.preonboarding.ticket.application.policy.discount;

public class DefaultDiscountPolicy implements DiscountPolicy {

	@Override
	public Long calculateAmount(int performanceAmount, Long reservationAmount) {
		long result = reservationAmount - performanceAmount;
		if (result < 0) {
			throw new IllegalArgumentException("잔액이 부족합니다.");
		}
		return result;
	}
}
