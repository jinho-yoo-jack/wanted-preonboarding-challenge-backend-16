package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private PerformanceInfo performanceInfo;
    private UserInfo userInfo;
    private Integer amount;
    private Integer gate;
    private Character line;
    private Integer seat;
}
