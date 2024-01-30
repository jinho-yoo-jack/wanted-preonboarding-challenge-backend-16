package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * {@code Reservation} 클래스는 티켓 시스템의 "예약" Entity입니다.
 * JPA를 통해 'reservation' 테이블과 매핑됩니다.
 */
@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
  private UUID performanceId;
  @Column(nullable = false)
  private String name;
  @Column(nullable = false, name = "phone_number")
  private String phoneNumber;
  @Column(nullable = false)
  private int round;
  private int gate;
  private char line;
  private int seat;

  /**
   * {@link ReserveInfo}를 받아 {@link Reservation} 객체를 생성합니다.
   *
   * @param info 예약의 정보를 포함하는 {@link ReserveInfo} 객체.
   * @return 생성된 {@link Reservation}
   */
  public static Reservation of(ReserveInfo info) {
    return Reservation.builder()
        .performanceId(info.getPerformanceId())
        .name(info.getReservationName())
        .phoneNumber(info.getReservationPhoneNumber())
        .round(info.getRound())
        .gate(1)
        .line(info.getLine())
        .seat(info.getSeat())
        .build();
  }
}
