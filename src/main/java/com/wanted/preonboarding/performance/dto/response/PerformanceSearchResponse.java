package com.wanted.preonboarding.performance.dto.response;

import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PerformanceSearchResponse {
    private String performanceName;
    private Integer round;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reserveStatus;

    private PerformanceSearchResponse() {

    }

    public static PerformanceSearchResponse of(PerformanceInfo performanceInfo) {
        PerformanceSearchResponse response = new PerformanceSearchResponse();
        response.performanceName = performanceInfo.getPerformanceName();
        response.round = performanceInfo.getRound();
        response.startDate = performanceInfo.getStartDate();
        response.endDate = performanceInfo.getEndDate();
        response.reserveStatus = performanceInfo.getReserveStatus().name();
        return response;
    }
}
