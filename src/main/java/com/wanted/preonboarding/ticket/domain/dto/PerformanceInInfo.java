package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PerformanceInInfo {
    private UUID performanceId;
    private int round;
    private char line;
    private int seat;
    private int gate;
    private String reserve;
}
