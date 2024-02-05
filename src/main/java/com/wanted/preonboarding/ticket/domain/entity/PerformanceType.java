package com.wanted.preonboarding.ticket.domain.entity;

public enum PerformanceType {
    NONE(0, "NONE"),
    CONCERT(1, "CONCERT"),
    EXHIBITION(2, "EXHIBITION");

    private final int category;
    private final String typeName;

    PerformanceType(int category, String typeName) {
        this.category = category;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public int getCategory() {
        return category;
    }

}
