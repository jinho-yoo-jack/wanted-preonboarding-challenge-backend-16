package com.wanted.preonboarding.performanceSeat.domain.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class SeatSoldOutEvent {

    private final UUID performanceId;

    public static SeatSoldOutEvent of(UUID uuid) {
        return new SeatSoldOutEvent(uuid);
    }
}
