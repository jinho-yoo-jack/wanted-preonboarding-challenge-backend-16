package com.wanted.preonboarding.ticket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PerformanceIdRequest {
    private String performanceName;
    private int performanceType;
    private int performanceRound;
    private Date startDate;

}
