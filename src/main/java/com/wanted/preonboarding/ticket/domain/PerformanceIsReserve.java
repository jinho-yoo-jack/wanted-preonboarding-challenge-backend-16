package com.wanted.preonboarding.ticket.domain;

public enum PerformanceIsReserve {
    ENABLE("활성화"), DISABLE("비활성화");

    private String description;

    PerformanceIsReserve(String description) {
        this.description = description;
    }
}
