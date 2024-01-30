package com.wanted.preonboarding.ticket.domain.performance.model;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ReserveState {
    RESERVED("disable", "예약됨"),
    ENABLE("enable", "예약 가능");

    private final String command;
    private final String description;

    ReserveState(String command, String description) {
        this.command = command;
        this.description = description;
    }

    public static ReserveState convertToEnum(String command) {
        return Arrays.stream(ReserveState.values())
            .filter(state -> state.command.equals(command))
            .findFirst()
            .orElseThrow(IllegalArgumentException::new);
    }
}
