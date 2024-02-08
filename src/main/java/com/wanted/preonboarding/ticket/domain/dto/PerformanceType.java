package com.wanted.preonboarding.ticket.domain.dto;

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

    public static PerformanceType fromCategory(int category) {
        return Arrays.stream(values())
                .filter(type -> type.category == category)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 카테고리 입니다: " + category));
    }

}
