package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Performance performance;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, name = "phone_number")
	private String phoneNumber;
	@Column(nullable = false)
	private int round;
	private int gate;
	private String line;
	private int seat;

	@OneToOne
	private PerformanceSeatInfo performanceSeatInfo;

	public static Reservation of(ReserveInfo info, Performance performance, PerformanceSeatInfo seatInfo) {
		return Reservation.builder()
			.performance(performance)
			.name(info.getReservationName())
			.phoneNumber(info.getReservationPhoneNumber())
			.round(info.getRound())
			.gate(seatInfo.getGate())
			.performanceSeatInfo(seatInfo)
			.line(info.getLine())
			.seat(info.getSeat())
			.build();
	}
}
