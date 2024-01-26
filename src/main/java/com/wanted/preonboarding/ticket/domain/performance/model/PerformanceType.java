package com.wanted.preonboarding.ticket.domain.performance.model;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum PerformanceType {
    NONE(0),
    CONCERT(1),
    EXHIBITION(2);

    private final int index;

    PerformanceType(int index) {
        this.index = index;
    }

    public static PerformanceType convertToEnum(int index) {
        return Arrays.stream(PerformanceType.values())
            .filter(type -> type.index == index)
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
