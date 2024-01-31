package com.wanted.preonboarding.performance.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

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
