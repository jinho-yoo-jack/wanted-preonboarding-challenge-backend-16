package com.wanted.preonboarding.ticket.domain.dto;

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
