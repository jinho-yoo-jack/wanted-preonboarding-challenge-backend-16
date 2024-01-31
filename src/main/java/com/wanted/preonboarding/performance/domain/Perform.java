package com.wanted.preonboarding.performance.domain;

import com.wanted.preonboarding.performance.domain.vo.Gate;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Perform {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "performance_id",nullable = false)
	private Performance performance;

	@OneToMany(mappedBy = "perform",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<ReservationCancelSubscribe> observers;

	@Column(nullable = false)
	private int round;

	@Column(nullable = false)
	private LocalDate startDate;

	@Column(nullable = false, name = "reservation_available")
	private boolean reservationAvailable;

	@Embedded
	private Gate gate;

	private Perform(Performance performance, Gate gate,  int round,
		LocalDate startDate, boolean reservationAvailable) {
		this.performance = performance;
		this.gate = gate;
		this.round = round;
		this.startDate = startDate;
		this.reservationAvailable = reservationAvailable;
	}

	public static Perform create(Performance performance, Gate gate, int round,
		LocalDate startDate, boolean reservationAvailable) {
		return new Perform(performance, gate,round,startDate,reservationAvailable);
	}

	public void soldOut() {
		this.reservationAvailable = false;
	}

}
