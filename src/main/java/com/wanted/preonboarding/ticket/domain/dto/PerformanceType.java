package com.wanted.preonboarding.ticket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

public enum PerformanceType {
    NONE(0),
    CONCERT(1), // 공연
    EXHIBITION(2);  // 전시회

    private final int category;

    PerformanceType(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

}
