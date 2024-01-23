package com.wanted.preonboarding.ticket.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(
	uniqueConstraints =
	@UniqueConstraint(
		name = "performance_seat_info_unique",
		columnNames = {"performance_id", "round", "line", "seat"}
	)
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class PerformanceSeatInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@ManyToOne
	private Performance performance;
	@Column(nullable = false)
	private int round;

	@Column(nullable = false)
	private int gate;

	@Column(nullable = false)
	private String line;

	@Column(nullable = false)
	private int seat;

	@Column(nullable = false, name = "is_reserve", columnDefinition = "varchar(255) default 'enable'")
	private String isReserve;

	@CreatedDate
	@NotNull
	private LocalDateTime createdAt;

	@LastModifiedDate
	@NotNull
	private LocalDateTime updatedAt;
}
