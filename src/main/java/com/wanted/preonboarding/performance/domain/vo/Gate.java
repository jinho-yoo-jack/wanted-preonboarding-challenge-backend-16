package com.wanted.preonboarding.performance.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gate {

	@Column(nullable = false)
	private int gate;

	private Gate(int gate) {
		this.gate = gate;
	}

	public static Gate create(int gate) {
		return new Gate(gate);
	}
}
