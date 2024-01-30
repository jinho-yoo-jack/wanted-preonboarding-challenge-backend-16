package com.wanted.preonboarding.performance.domain.discount_policy;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="discount_type")
public abstract class DiscountPolicy{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	public abstract int getDiscountAmount(int price);
}
