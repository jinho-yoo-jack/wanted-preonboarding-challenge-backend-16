package com.wanted.preonboarding.domain.dto.performance;

import com.wanted.preonboarding.domain.entity.Performance;
import com.wanted.preonboarding.domain.entity.SeatInfo;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * 공연 좌석 정보를 다루는 DTO
 *
 * <p>해당 DTO는 특정 공연의 좌석 정보를 전달하기 위해 사용합니다.</p>
 * <p>공연 ID, 공연 명, 공연 회차, 입장 게이트, 좌석 줄과 좌석 번호를 포함합니다.</p>
 *
 * @see Performance
 * @see SeatInfo
 */
@Data
@Builder
public class PerformanceSeatDto {
  private UUID performanceId;
  private String performanceName;
  private int round;
  private int gate;
  private String line;
  private int seat;

  public static PerformanceSeatDto of(SeatInfo seat) {
    return PerformanceSeatDto.builder()
        .performanceId(seat.getPerformance().getId())
        .performanceName(seat.getPerformance().getName())
        .round(seat.getPerformance().getRound())
        .gate(seat.getGate())
        .line(seat.getLine())
        .seat(seat.getSeat())
        .build();
  }
}
