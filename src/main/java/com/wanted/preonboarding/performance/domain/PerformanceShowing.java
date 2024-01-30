package com.wanted.preonboarding.performance.domain;

import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceShowing {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@OneToOne
	@JoinColumn(name = "performenc_id",nullable = false)
	private Performance performance;

	@OneToOne
	@JoinColumn(name = "showroom_id", nullable = false)
	private PerformanceShowroom performanceShowroom;

	@Column(nullable = false)
	private int round;

	@Column(nullable = false)
	private LocalDate startDate;
	@Column(nullable = false, name = "reservation_available")
	private boolean reservationAvailable;

	private PerformanceShowing(Performance performance, PerformanceShowroom performanceShowroom,  int round,
		LocalDate startDate, boolean reservationAvailable) {
		this.performance = performance;
		this.performanceShowroom = performanceShowroom;
		this.round = round;
		this.startDate = startDate;
		this.reservationAvailable = reservationAvailable;
	}

	public static PerformanceShowing create(Performance performance, PerformanceShowroom performanceShowroom, int round,
		LocalDate startDate, boolean reservationAvailable) {
		return new PerformanceShowing(performance, performanceShowroom,round,startDate,reservationAvailable);
	}


	public PerformanceReservation reserve(ReservationRequest request) {
		int fee = performance.calculateFee();
		return PerformanceReservation.of(this,request,fee);
	}
}
