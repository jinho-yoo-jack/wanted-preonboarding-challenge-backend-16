package com.wanted.preonboarding.ticket.domain.entity;

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

    public static PerformanceType of(int category) {
        return Arrays.stream(PerformanceType.values())
                .filter(v -> v.getCategory() == category)
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 카테고리입니다."));
    }
}
