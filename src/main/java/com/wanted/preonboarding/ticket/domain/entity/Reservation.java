package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.ReservationStatus;
import com.wanted.preonboarding.ticket.domain.dto.ReservationRequest;
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
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, name = "phone_number")
	private String phoneNumber;

	@OneToOne
	@JoinColumn(name = "showing_id", nullable = false)
	private Showing showing;

	@Enumerated(EnumType.STRING)
	ReservationStatus reservationStatus; // 예약; 취소

	@Embedded
	private PerformanceSeatInfo performanceSeatInfo;

	private int fee;

	private Reservation(Showing showing, String name, String phoneNumber,
		PerformanceSeatInfo performanceSeatInfo, ReservationStatus reservationStatus, int fee) {
		this.showing = showing;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.performanceSeatInfo = performanceSeatInfo;
		this.reservationStatus = reservationStatus;
		this.fee = fee;
	}

	public static Reservation of(Showing showing, ReservationRequest request, int fee) {

		return new Reservation(
			showing,
			request.reservationName(),
			request.reservationPhoneNumber(),
			PerformanceSeatInfo.create(request.line(), request.seat()),
			ReservationStatus.RESERVE,
			fee);
	}

}
