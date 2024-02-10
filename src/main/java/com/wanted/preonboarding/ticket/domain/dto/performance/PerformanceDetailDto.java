package com.wanted.preonboarding.ticket.domain.dto.performance;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceType;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.Builder;
import lombok.Data;

/**
 * 공연의 상세 정보를 나타내는 DTO 클래스입니다.
 * 엔티티 객체로부터 DTO 객체를 생성하기 위한 정적 메서드를 지원합니다.
 *
 * @see Performance
 */
@Builder
@Data
public class PerformanceDetailDto {
  private String  performanceName;
  private int     round;
  private LocalDateTime startDate;
  private String  isReserve;
  private int     price;
  private String  type;

  /**
   * 공연의 상세 정보를 {@link Performance} 엔티티 객체로부터 생성합니다.
   *
   * @param entity 변환할 {@link Performance} 엔티티 객체
   * @return 변환된 {@link Performance} 객체
   */
  public static PerformanceDetailDto of(Performance entity) {
    return PerformanceDetailDto.builder()
        .performanceName(entity.getName())
        .round(entity.getRound())
        .startDate(entity.getStartDate())
        .isReserve(entity.getIsReserve())
        .price(entity.getPrice())
        .type(convertCodeToName(entity.getType().getCategory()))
        .build();
  }
  private static String convertCodeToName(int code) {
    return Arrays.stream(PerformanceType.values()).filter(value -> value.getCategory() == code)
        .findFirst()
        .orElse(PerformanceType.NONE)
        .name();
  }
}
