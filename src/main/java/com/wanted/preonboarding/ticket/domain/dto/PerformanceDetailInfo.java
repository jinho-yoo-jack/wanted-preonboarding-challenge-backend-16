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
