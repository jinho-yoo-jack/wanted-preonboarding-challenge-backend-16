package com.wanted.preonboarding.domain.dto.performance;

import com.wanted.preonboarding.domain.entity.Performance;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/**
 * 공연의 정보를 나타내는 DTO 클래스입니다.
 * 엔티티 객체로부터 DTO 객체를 생성하기 위한 정적 메서드를 지원합니다.
 *
 * @see Performance
 */
@Data
@Builder
public class PerformanceInfoDto {
  private UUID    id;
  private String  performanceName;
  private int     round;
  private LocalDateTime startDate;
  private String  isReserve;

  /**
   * 공연의 정보를 {@link Performance} 엔티티 객체로부터 생성합니다.
   *
   * @param entity 변환할 {@link Performance} 엔티티 객체
   * @return 변환된 {@link Performance} 객체
   */
  public static PerformanceInfoDto of(Performance entity) {
    return PerformanceInfoDto.builder()
        .id(entity.getId())
        .performanceName(entity.getName())
        .round(entity.getRound())
        .startDate(entity.getStartDate())
        .isReserve(entity.getIsReserve())
        .build();
  }
}
