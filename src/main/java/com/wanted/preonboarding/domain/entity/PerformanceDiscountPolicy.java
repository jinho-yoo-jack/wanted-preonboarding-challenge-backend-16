package com.wanted.preonboarding.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "discount_policy")
public class PerformanceDiscountPolicy extends BaseEntity {
  @Column(name = "id")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "performance_id", columnDefinition = "BINARY(16)")
  private UUID performanceId;

  @Column(name = "type")
  private String type;

  @Column(name = "name")
  private String name;

  @Column(name = "started_at")
  private LocalDateTime startedAt;

  @Column(name = "ended_at")
  private LocalDateTime endedAt;

  @Column(name = "rate")
  private BigDecimal rate;

  @Column(name = "discount_fee")
  private int fee;
}
