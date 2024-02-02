package com.wanted.preonboarding.performance.domain.discount_policy;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@DiscriminatorValue(value="percent")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
