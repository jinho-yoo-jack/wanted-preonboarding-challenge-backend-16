package com.wanted.preonboarding.push.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
@Builder
public class AlarmVO {

    private UUID performanceId;
    private String performanceName;
    private int round;
    private int seat;
    private Date startDate;
}
