package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Builder;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class PerformanceInfoResponse {
    private UUID performanceId;
    private String performanceName;
    private String performanceType;
    private Date startDate;
    private String isReserve;
}
