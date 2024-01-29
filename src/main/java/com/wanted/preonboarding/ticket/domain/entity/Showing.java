package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReservationRequest;
import jakarta.persistence.CascadeType;
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
public class Showing {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@OneToOne
	@JoinColumn(name = "performenc_id")
	private Performance performance;

	@OneToOne
	@JoinColumn(name = "showroom_id")
	private Showroom showroom;

	@Column(nullable = false)
	private int round;

	@Column(nullable = false)
	private LocalDate startDate;
	@Column(nullable = false, name = "reservation_available")
	private boolean reservationAvailable;

	private Showing(Performance performance, Showroom showroom,  int round,
		LocalDate startDate, boolean reservationAvailable) {
		this.performance = performance;
		this.showroom = showroom;
		this.round = round;
		this.startDate = startDate;
		this.reservationAvailable = reservationAvailable;
	}

	public static Showing create(Performance performance, Showroom showroom, int round,
		LocalDate startDate, boolean reservationAvailable) {
		return new Showing(performance,showroom,round,startDate,reservationAvailable);
	}


	public Reservation reserve(ReservationRequest request) {
		int fee = performance.calculateFee();
		return Reservation.of(this,request,fee);
	}

	public int getFee() {
		return performance.calculateFee();
	}
}
