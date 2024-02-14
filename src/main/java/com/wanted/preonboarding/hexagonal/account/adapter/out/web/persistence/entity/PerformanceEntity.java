package com.wanted.preonboarding.hexagonal.account.adapter.out.web.persistence.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
public class PerformanceEntity {
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
	 @Column(nullable = false)
	 private Date start_date;
	 @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
	 private String isReserve;
	 
}
