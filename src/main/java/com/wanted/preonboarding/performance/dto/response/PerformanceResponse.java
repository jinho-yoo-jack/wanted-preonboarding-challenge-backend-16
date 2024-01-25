package com.wanted.preonboarding.performance.dto.response;

import com.wanted.preonboarding.performance.dto.PerformanceInfo;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class PerformanceResponse {
    private UUID performanceId;
    private String performanceName;
    private Integer price;
    private Integer round;
    private Integer performanceType;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String reserveStatus;

    private PerformanceResponse() {

    }

    public static PerformanceResponse of(PerformanceInfo performanceInfo) {
        PerformanceResponse response = new PerformanceResponse();
        response.performanceId = performanceInfo.getPerformanceId();
        response.performanceName = performanceInfo.getPerformanceName();
        response.price = performanceInfo.getPrice();
        response.round = performanceInfo.getRound();
        response.performanceType = performanceInfo.getPerformanceType().getCategory();
        response.startDate = performanceInfo.getStartDate();
        response.endDate = performanceInfo.getEndDate();
        response.reserveStatus = performanceInfo.getReserveStatus().name();
        return response;
    }

}
