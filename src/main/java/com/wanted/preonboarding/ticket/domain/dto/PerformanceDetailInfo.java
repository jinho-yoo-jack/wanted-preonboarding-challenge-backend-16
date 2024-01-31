package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

/**
 *  UUID performanceId: 공연 ID
 *  String performanceName: 공연 이름
 *  int performanceRound: 공연 회차
 *  LocalDateTime startDate: 공연 시작 일자
 *  String isReserve: 예약 가능 여부
 *  List<SeatInfo> seatInfo: 좌석 정보 리스트
 */

@Getter
@Builder
public class PerformanceDetailInfo {
    private UUID performanceId;
    private String performanceName;
    private int performanceRound;
    private LocalDateTime startDate;
    private String isReserve;
    private List<SeatInfo> seatInfo;

    public void haveReserveAbleSeat(List<SeatInfo> seatInfo){
        this.seatInfo = seatInfo;
    }

    public static PerformanceDetailInfo of(Performance entity){
        return PerformanceDetailInfo.builder()
            .performanceId(entity.getId())
            .performanceName(entity.getName())
            .performanceRound(entity.getRound())
            .startDate(entity.getStart_date())
            .isReserve(entity.getIsReserve())
            .build();
    }

}
