package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * {@link Performance}의 자리를 나타내는 Entity입니다.
 * {@link Reservation}에서 예약 시, 유저가 선택하는 자리입니다.
 */
@Data
@Entity
@Table(name = "performance_seat_info")
public class SeatInfo {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "gate")
  private int     gate;

  @Column(name = "line", columnDefinition = "char(2)")
  private String  line;

  @Column(name = "seat")
  private int     seat;

  @ManyToOne
  @JoinColumn(name = "performance_id")
  private Performance performance;
}