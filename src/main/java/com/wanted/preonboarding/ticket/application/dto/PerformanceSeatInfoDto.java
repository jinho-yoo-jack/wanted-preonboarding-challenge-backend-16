package com.wanted.preonboarding.ticket.application.dto;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PerformanceSeatInfoDto {
    private Integer id;
    private SeatInfo seatInfo;
    private Boolean isReservable;

    public static PerformanceSeatInfoDto of(final PerformanceSeatInfo entity) {
        return PerformanceSeatInfoDto.builder()
                .id(entity.getId())
                .seatInfo(entity.getSeatInfo())
                .isReservable(entity.getIsReservable())
                .build();
    }
}
