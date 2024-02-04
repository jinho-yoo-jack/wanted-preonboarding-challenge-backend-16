package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Getter;

public enum PerformanceType {
    NONE(0, "None"),
    CONCERT(1, "Concert"),
    EXHIBITION(2, "Exhibition");

    @Getter
    private final int category;
    private final String description;

    PerformanceType(int category, String description) {

        this.category = category;
        this.description = description;
    }


}
