package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import java.util.Arrays;
import java.util.Date;
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
public class PerformanceInfo {
  private UUID performanceId;
  private String performanceName;
  private String performanceType;
  private Date startDate;
  private String isReserve;

  /**
   * 공연의 정보를 {@link Performance} 엔티티 객체로부터 생성합니다.
   *
   * @param entity 변환할 {@link Performance} 엔티티 객체
   * @return 변환된 {@link PerformanceInfo} 객체
   */
  public static PerformanceInfo of(Performance entity) {
    return PerformanceInfo.builder()
        .performanceId(entity.getId())
        .performanceName(entity.getName())
        .performanceType(convertCodeToName(entity.getType()))
        .startDate(entity.getStartDate())
        .isReserve(entity.getIsReserve())
        .build();
  }

  private static String convertCodeToName(int code) {
    return Arrays.stream(PerformanceType.values()).filter(value -> value.getCategory() == code)
        .findFirst()
        .orElse(PerformanceType.NONE)
        .name();
  }

}
