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
public class PerformanceShowingObserver {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_reservation_id")
	private PerformanceShowing showing;

	@Column(nullable = false)
	private String userId;

	private PerformanceShowingObserver(PerformanceShowing showing, String userId) {
		this.showing = showing;
		this.userId = userId;
	}

	public static PerformanceShowingObserver create(PerformanceShowing showing, UUID userId) {
		return new PerformanceShowingObserver(showing, userId.toString());
	}
}
