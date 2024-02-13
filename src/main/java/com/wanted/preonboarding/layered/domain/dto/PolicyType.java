package com.wanted.preonboarding.layered.domain.dto;

public enum PolicyType {
    NONE(0),
    TELECOME(1),
    NEW(2);

    private final int category;

    PolicyType(int category) {
        this.category = category;
    }

    public int getCategory() {
        return category;
    }

}
