package com.wanted.preonboarding.ticket.domain.info;

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
