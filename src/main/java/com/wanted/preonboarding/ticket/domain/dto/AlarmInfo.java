package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class AlarmInfo {
    private int id;
    private UUID performanceId;
    private String performanceName;
    private String phoneNumber;
    private String name;
}
