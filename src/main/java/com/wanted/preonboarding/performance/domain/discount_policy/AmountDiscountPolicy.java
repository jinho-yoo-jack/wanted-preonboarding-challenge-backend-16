package com.wanted.preonboarding.performance.domain.discount_policy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.PrimaryKeyJoinColumn;

@DiscriminatorValue(value="amount")
@PrimaryKeyJoinColumn(name = "id")
public class AmountDiscountPolicy extends DiscountPolicy{

	private int amount;

	public AmountDiscountPolicy(int amount) {
		this.amount = amount;
	}

	@Override
	public int getDiscountAmount(int price) {
		return amount;
	}
}
