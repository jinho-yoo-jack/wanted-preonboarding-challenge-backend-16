package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "VARBINARY(16)", name = "performance_id")
	private UUID performanceId;
	@Column(nullable = false, name = "performance_name")
	private String peformanceName;
	@Column(nullable = false)
	private int price;
	@Column(nullable = false)
	private int round;
	@Column(nullable = false)
	private int type;
	@Column(nullable = false)
	private LocalDate start_date;
	@Column(nullable = false, name = "reserved")
	private boolean reserved = false;

	public void reserved() {
		this.reserved = true;
	}
}
