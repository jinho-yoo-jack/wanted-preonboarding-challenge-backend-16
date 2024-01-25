package com.wanted.preonboarding.performance.domain.constant;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

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

    /**
     * Category factory method
     * @param category
     * @return PerformanceType
     */
    public static PerformanceType of(int category) {
        return Arrays.stream(PerformanceType.values())
                .filter(performanceType -> performanceType.category == category)
                .findFirst()
                .orElse(PerformanceType.NONE);
    }

}
