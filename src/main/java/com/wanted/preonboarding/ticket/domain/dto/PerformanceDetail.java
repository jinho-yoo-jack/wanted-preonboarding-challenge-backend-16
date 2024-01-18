package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

import static com.wanted.preonboarding.ticket.application.util.TimeFormatter.convertToReadableFormat;
import static com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo.convertToName;

@Value
@Builder
public class PerformanceDetail {

    String name;
    Integer price;
    Integer round;
    String type;
    String startDate;
    int seatAvailable;
    List<SeatInfo> seatInfoList;

    @Value
    @Builder
    public static class SeatInfo {
        int gate;
        String line;
        int seat;
    }

    public static PerformanceDetail of(List<PerformanceSeatInfo> seatInfoList) {
        if (seatInfoList == null || seatInfoList.isEmpty()) {
            throw new IllegalArgumentException("좌석 정보가 존재하지 않습니다.");
        }

        Performance performance = seatInfoList.get(0).getPerformance();
        return PerformanceDetail.builder()
                .name(performance.getName())
                .price(performance.getPrice())
                .round(performance.getRound())
                .type(convertToName(performance.getType()))
                .startDate(convertToReadableFormat(performance.getStartDate()))
                .seatAvailable(seatInfoList.size())
                .seatInfoList(convertToSeatInfoList(seatInfoList))
                .build();
    }

    private static List<SeatInfo> convertToSeatInfoList(List<PerformanceSeatInfo> seatInfoList) {
        return seatInfoList.stream()
                .map(seatInfo -> SeatInfo.builder()
                        .gate(seatInfo.getGate())
                        .line(seatInfo.getLine())
                        .seat(seatInfo.getSeat())
                        .build())
                .toList();
    }

}
