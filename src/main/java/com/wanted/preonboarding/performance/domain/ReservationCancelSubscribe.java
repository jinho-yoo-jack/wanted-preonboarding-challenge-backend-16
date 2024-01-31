package com.wanted.preonboarding.performance.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationCancelSubscribe {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "perfor_id")
	private Perform perform;

	@Column(nullable = false)
	private String userId;

	private ReservationCancelSubscribe(Perform perform, String userId) {
		this.perform = perform;
		this.userId = userId;
	}

	public static ReservationCancelSubscribe create(Perform perform, UUID userId) {
		return new ReservationCancelSubscribe(perform, userId.toString());
	}
}
