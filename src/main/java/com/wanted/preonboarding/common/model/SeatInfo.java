package com.wanted.preonboarding.common.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Embeddable
public class SeatInfo {

    private int round;

    private int gate;

    private char line;

    private int seat;
}
