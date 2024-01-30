package com.wanted.preonboarding.performance.domain;

import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import com.wanted.preonboarding.performance.domain.vo.PerformanceSeatInfo;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceReservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, name = "phone_number")
	private String phoneNumber;

	@OneToOne
	@JoinColumn(name = "showing_id", nullable = false)
	private PerformanceShowing performanceShowing;

	@Enumerated(EnumType.STRING)
	ReservationStatus reservationStatus; // 예약; 취소

	@Embedded
	private PerformanceSeatInfo performanceSeatInfo;

	private int fee;

	private PerformanceReservation(PerformanceShowing performanceShowing, String name, String phoneNumber,
		PerformanceSeatInfo performanceSeatInfo, ReservationStatus reservationStatus, int fee) {
		this.performanceShowing = performanceShowing;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.performanceSeatInfo = performanceSeatInfo;
		this.reservationStatus = reservationStatus;
		this.fee = fee;
	}

	public static PerformanceReservation of(PerformanceShowing performanceShowing, ReservationRequest request, int fee) {

		return new PerformanceReservation(
			performanceShowing,
			request.reservationName(),
			request.reservationPhoneNumber(),
			PerformanceSeatInfo.create(request.line(), request.seat()),
			ReservationStatus.RESERVE,
			fee);
	}

}
