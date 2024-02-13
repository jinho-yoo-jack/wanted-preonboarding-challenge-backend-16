package com.wanted.preonboarding.layered.domain.entity.ticketing;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDiscountPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String type;
    @Column(nullable = true)
    private BigDecimal rate;
    @Column(nullable = true, name = "discountFee")
    private int discountFee;
    @Column(nullable = false, name = "started_at")
    private Date startedAt;
    @Column(nullable = false, name = "ended_at")
    private Date endedAt;
}
