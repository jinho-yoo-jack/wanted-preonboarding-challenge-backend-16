package com.wanted.preonboarding.performance.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformancePlace {

	@Column(nullable = false)
	private int gate;

	private PerformancePlace(int gate) {
		this.gate = gate;
	}

	public static PerformancePlace create(int gate) {
		return new PerformancePlace(gate);
	}
}
