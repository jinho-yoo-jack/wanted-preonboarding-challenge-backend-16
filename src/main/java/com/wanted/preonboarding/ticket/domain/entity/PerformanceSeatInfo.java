package com.wanted.preonboarding.ticket.domain.entity;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static java.time.LocalDateTime.now;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class PerformanceSeatInfo {
	@Id @GeneratedValue(strategy = IDENTITY)
	@Column(name = "performance_seat_info_id")
	private Long id;

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "performance_id")
	private Performance performance;

	private int round;
	private int gate;
	private String line;
	private int seat;
	private String isReserve;
	private LocalDateTime createdAt = now();
	private LocalDateTime updatedAt = now();

	public static PerformanceSeatInfo create(Performance performance, int round, int gate, String line, int seat, String isReserve) {
		PerformanceSeatInfo entity = new PerformanceSeatInfo();
		entity.performance = performance;
		entity.round = round;
		entity.gate = gate;
		entity.line = line;
		entity.seat = seat;
		entity.isReserve = isReserve;
		return entity;
	}
}
