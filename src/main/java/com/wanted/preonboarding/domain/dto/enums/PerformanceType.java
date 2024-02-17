package com.wanted.preonboarding.domain.dto.enums;

/**
 * 공연의 타입을 나타내는 Enum 클래스입니다.
 */
public enum PerformanceType {
  NONE(0),
  CONCERT(1),
  EXHIBITION(2);

  private final int category;

  PerformanceType(int category) {
    this.category = category;
  }

  public int getCategory() {
    return category;
  }
}
