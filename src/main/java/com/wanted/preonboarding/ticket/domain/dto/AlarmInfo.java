package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Alarm;
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
