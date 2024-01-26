package com.wanted.preonboarding.performanceSeat.domain.dto;

import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performanceSeat.domain.entity.PerformanceSeatInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
@Builder
public class PerformanceSeatResponse {

    private final SeatInfo seatInfo;

    private final String isReserve;

    public static PerformanceSeatResponse from(PerformanceSeatInfo performanceSeatInfo) {
        return PerformanceSeatResponse.builder()
                .seatInfo(performanceSeatInfo.getSeatInfo())
                .isReserve(performanceSeatInfo.getReserveState())
                .build();
    }
}
