package com.wanted.preonboarding.ticket.domain.enums;

import lombok.Getter;

@Getter
public enum PerformanceType {

    CONCERT(0),

    EXHIBITION(1),

    NONE(2);

    private final int category;

    PerformanceType(int category) {
        this.category = category;
    }

    public boolean isSameCategory(PerformanceType type) {
        return this.category == type.category;
    }
}
