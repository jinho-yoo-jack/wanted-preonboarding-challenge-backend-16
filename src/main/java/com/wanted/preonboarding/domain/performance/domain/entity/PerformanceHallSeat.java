package com.wanted.preonboarding.domain.performance.domain.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "performance_hall_seat")
@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceHallSeat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "hall_id", columnDefinition = "bigint unsigned NULL comment '공연/전시장 ID'")
	private Long hallId;

	@Column(name = "gate", columnDefinition = "int not null comment '게이트'")
	private int gate;
	@Column(name = "line", columnDefinition = "char(2) not null comment '열'")
	private char line;
	@Column(name = "seat", columnDefinition = "int not null comment '좌석'")
	private int seat;

}
