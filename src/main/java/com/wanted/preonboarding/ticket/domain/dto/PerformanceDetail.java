package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class PerformanceDetail {
    private UUID performanceId;
    private String name;
    private int round;
    private int seat;
    private Date startDate;
}