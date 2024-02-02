package com.wanted.preonboarding.performance.domain.discount_policy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@DiscriminatorValue(value="none")
@PrimaryKeyJoinColumn(name = "id")
public class NoneDiscountPolicy extends DiscountPolicy{

	@Override
	public int getDiscountAmount(int price) {
		return 0;
	}
}
