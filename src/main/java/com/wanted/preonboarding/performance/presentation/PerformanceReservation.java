package com.wanted.preonboarding.performance.presentation;

import com.wanted.preonboarding.performance.domain.Perform;
import com.wanted.preonboarding.performance.domain.vo.PerformanceSeatInfo;
import com.wanted.preonboarding.performance.domain.vo.ReservationStatus;
import com.wanted.preonboarding.performance.presentation.dto.ReservationRequest;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
public class PerformanceReservation {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, name = "phone_number")
	private String phoneNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "perform_id", nullable = false)
	private Perform perform;

	@Enumerated(EnumType.STRING)
	private ReservationStatus reservationStatus; // 예약; 취소

	@Embedded
	private PerformanceSeatInfo performanceSeatInfo;

	@Column(nullable = false)
	private int fee;

	private PerformanceReservation(Perform perform, String name, String phoneNumber,
		PerformanceSeatInfo performanceSeatInfo, ReservationStatus reservationStatus, int fee) {
		this.perform = perform;
		this.name = name;
		this.phoneNumber = phoneNumber;
		this.performanceSeatInfo = performanceSeatInfo;
		this.reservationStatus = reservationStatus;
		this.fee = fee;
	}

	public static PerformanceReservation of(Perform perform, ReservationRequest request, int fee) {
		PerformanceReservation reservation = new PerformanceReservation(
			perform,
			request.reservationName(),
			request.reservationPhoneNumber(),
			PerformanceSeatInfo.create(request.line(), request.seat()),
			ReservationStatus.RESERVE,
			fee);

		perform.addReservation(reservation);
		return reservation;
	}

	public int cancel(){
		int refundAmount = refundPolicyStub();
		this.reservationStatus = ReservationStatus.CANCEL;
		return refundAmount;
	}

	private int refundPolicyStub() {
		return fee;
	}

	public String getSeatInfoToString() {
		return getPerformanceSeatInfo().getLine() +":"+this.performanceSeatInfo.getSeat();
	}
}
