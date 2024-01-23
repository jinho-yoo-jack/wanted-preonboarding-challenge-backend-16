package com.wanted.preonboarding.ticket.domain.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.antlr.v4.runtime.misc.NotNull;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Performance {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private int price;
	@Column(nullable = false)
	private int round;
	@Column(nullable = false)
	private int type;
	@CreatedDate
	@NotNull
	private LocalDateTime start_date;

	@LastModifiedDate
	@NotNull
	private LocalDateTime update_date;
	@Column(nullable = false, name = "is_reserve", columnDefinition = "varchar(255) default 'disable'")
	private String isReserve;

	@OneToMany
	private List<PerformanceSeatInfo> performanceSeatInfoList = new ArrayList<>();

	/*
		예약 상태 변경
	 */
	public void updateIsReserve(String status) {
		this.isReserve = status;
	}
}
