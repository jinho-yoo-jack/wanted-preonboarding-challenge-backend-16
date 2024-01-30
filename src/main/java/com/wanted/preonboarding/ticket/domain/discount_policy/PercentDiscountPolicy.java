package com.wanted.preonboarding.ticket.domain.discount_policy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.PrimaryKeyJoinColumn;

@DiscriminatorValue(value="amount")
@PrimaryKeyJoinColumn(name = "id")
public class PercentDiscountPolicy extends DiscountPolicy {

	private double percent;

	public PercentDiscountPolicy(double percent) {
		this.percent = percent;
	}

	@Override
	public int getDiscountAmount(int price) {

		return (int) (price * percent/100);
	}
}
