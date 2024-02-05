package com.wanted.preonboarding.ticket.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.stream.Stream;

public enum PerformanceIsReserve {
    ENABLE("활성화"), DISABLE("비활성화");

    private String description;

    PerformanceIsReserve(String description) {
        this.description = description;
    }

    @JsonCreator
    public static PerformanceIsReserve parsing(String inputValue) {
        return Stream.of(values())
                .filter(performanceIsReserve -> performanceIsReserve.toString().equals(inputValue.toUpperCase()))
                .findFirst()
                .orElse(null);
    }
}
