package com.wanted.preonboarding.ticketing.domain.dto;

import lombok.Getter;

@Getter
public enum PerformanceType {
    NONE(0),
    CONCERT(1),
    EXHIBITION(2);

    private final int category;

    PerformanceType(int category) {
        this.category = category;
    }

}