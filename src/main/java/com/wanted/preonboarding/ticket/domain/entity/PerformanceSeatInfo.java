package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

import com.wanted.preonboarding.common.exception.NotReservedStateException;
import com.wanted.preonboarding.ticket.domain.info.SeatInfo;

@Table(name = "performance_seat_info",
	uniqueConstraints = { @UniqueConstraint(name = "performance_seat_info_unique",
		columnNames = {"performance_id", "round", "line", "seat"})})
@Entity
@Getter
@NoArgsConstructor
public class PerformanceSeatInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(columnDefinition = "VARBINARY(16)", nullable = false, name = "performance_id")
	private UUID performanceId;

	@Column(nullable = false)
	private int round;

	@Column(nullable = false)
	private int gate;

	@Embedded
	private SeatInfo seatInfo;

	@Column(nullable = false, name = "reserved")
	private boolean reserved;

	public void cancel() {
		if (!reserved) {
			throw new NotReservedStateException();
		}
		reserved = false;
	}

	public void reserved() {
		reserved = true;
	}
}
