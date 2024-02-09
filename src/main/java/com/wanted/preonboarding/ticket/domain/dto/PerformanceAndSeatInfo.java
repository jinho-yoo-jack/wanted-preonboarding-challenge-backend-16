package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

/**
 *  UUID performanceId: 공연 ID
 *  String performanceName: 공연 이름
 *  int performanceRound: 공연 회차
 *  LocalDateTime startDate: 공연 시작 일자
 *  String isReserve: 예약 가능 여부
 *  int seat: 좌석 행 정보
 *  char line: 좌석 열 정보
 */

@Getter
@Builder
public class PerformanceAndSeatInfo {
    private UUID performanceId;
    private String performanceName;
    private int performanceRound;
    private int performancePrice;
    private LocalDateTime startDate;
    private String isReserve;
    private int seat;
    private char line;

    public static PerformanceAndSeatInfo of(PerformanceSeatInfo entity){
        return PerformanceAndSeatInfo.builder()
            .performanceId(entity.getPerformance().getId())
            .performanceName(entity.getPerformance().getName())
            .performancePrice(entity.getPerformance().getPrice())
            .performanceRound(entity.getPerformance().getRound())
            .startDate(entity.getPerformance().getStartDate())
            .isReserve(entity.getIsReserve())
            .seat(entity.getSeat())
            .line(entity.getLine())
            .build();
    }
}
