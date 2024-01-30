package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 예약의 정보를 나타내는 DTO 클래스입니다.
 * DTO 객체를 다루기 위한, Getter, Setter 및 Builder를 제공합니다.
 *
 * @see Reservation
 */
@Getter
@Setter
@Builder
public class ReserveInfo {
  // 공연 및 전시 정보 + 예약자 정보
  private UUID performanceId;
  private String reservationName;
  private String reservationPhoneNumber;
  private String reservationStatus; // 예약; 취소;
  private long amount;
  private int round;
  private char line;
  private int seat;
}
