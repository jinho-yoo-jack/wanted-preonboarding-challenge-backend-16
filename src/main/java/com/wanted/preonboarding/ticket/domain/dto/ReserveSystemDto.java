package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ReserveSystemDto {
//    private PerformanceInfo performanceInfo;
//    private ReserveInfo reserveInfo;
    private ResponseReserveQueryDto responseQueryDto;
}
