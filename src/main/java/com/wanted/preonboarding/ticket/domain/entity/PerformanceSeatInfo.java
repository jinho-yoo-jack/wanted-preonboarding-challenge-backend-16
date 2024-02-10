package com.wanted.preonboarding.ticket.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PerformanceSeatInfo {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "performance_seat_info_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "performance_id")
	private Performance performance;

	private int round;
	private int gate;
	private char line;
	private int seat;
	private String isReserve;
	@OneToOne(mappedBy = "performanceSeatInfo")
	private WaitingList waitingList;
	private LocalDateTime createdAt = now();
	private LocalDateTime updatedAt = now();

	public static PerformanceSeatInfo create(Performance performance, int round, int gate, char line, int seat, String isReserve) {
		PerformanceSeatInfo entity = new PerformanceSeatInfo();
		entity.performance = performance;
		entity.round = round;
		entity.gate = gate;
		entity.line = line;
		entity.seat = seat;
		entity.isReserve = isReserve;
		return entity;
	}

	public void changeIsReserve() {
		this.isReserve = "disable";
	}
}
