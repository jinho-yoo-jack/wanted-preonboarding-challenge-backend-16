package com.wanted.preonboarding.performance.domain.entity;

import java.sql.Date;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceSeatInfo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
	private UUID performanceId;
	
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
