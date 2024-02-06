package com.wanted.preonboarding.performance.domain.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@Column( nullable = false, name = "performance_seat_id")
	private int performanceSeatId;
	
	@Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
	private UUID performanceId;
	
    @Column(nullable = false)
    private int round;
    
    @Column(nullable = false)
    private int gate;
    
    @Column(nullable = false)
    private String line;
    
    @Column(nullable = false)
    private int seat;
    
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String isReserve;
    
    @ManyToOne
    @JoinColumn(name = "performance_id", insertable = false, updatable = false)
    private Performance performance;
    
    
}
