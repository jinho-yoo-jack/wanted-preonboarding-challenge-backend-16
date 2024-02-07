package com.wanted.preonboarding.ticket.domain.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@code Reservation} 클래스는 티켓 시스템의 "예약" Entity입니다.
 * JPA를 통해 'reservation' 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Reservation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column(name = "completed", columnDefinition = "default false")
  private boolean completed;

  @ManyToOne
  @JoinColumn(name = "user_id", columnDefinition = "bigint")
  private UserInfo userInfo;

  @ManyToOne
  @JoinColumn(name = "seat_id")
  private SeatInfo seatInfo;

  /**
   * {@link UserInfo}와 {@link SeatInfo}를 받아 {@link Reservation} 객체를 생성합니다.
   *
   * @param user 예약자의 정보
   * @param seatInfo 예약하려는 좌석 정보
   * @return 생성된 {@link Reservation}
   */
  public static Reservation of(UserInfo user, SeatInfo seatInfo) {
    return Reservation.builder()
        .seatInfo(seatInfo)
        .userInfo(user)
        .build();
  }
}
