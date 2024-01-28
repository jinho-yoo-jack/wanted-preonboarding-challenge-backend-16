package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.application.common.exception.EntityNotFoundException;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import lombok.Builder;
import lombok.Value;

import java.util.List;

import static com.wanted.preonboarding.ticket.application.common.exception.ExceptionStatus.NOT_FOUND_INFO;
import static com.wanted.preonboarding.ticket.application.common.util.TimeFormatter.convertToReadableFormat;
import static com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfo.convertToName;
import static com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability.AVAILABLE;
import static com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability.valueOf;

@Value
@Builder
public class PerformanceDetail {

    String name;
    Integer price;
    Integer round;
    String type;
    String startDate;
    Integer seatTotal;
    Integer seatAvailable;
    List<SeatInfo> seatInfoList;

    @Value
    @Builder
    public static class SeatInfo {
        Integer gate;
        String line;
        Integer seat;
        String isReserved;
    }

    public static PerformanceDetail of(List<PerformanceSeatInfo> seatInfoList) {
        if (seatInfoList == null || seatInfoList.isEmpty()) {
            throw new EntityNotFoundException(NOT_FOUND_INFO);
        }
        Performance performance = seatInfoList.get(0).getPerformance();
        return PerformanceDetail.builder()
                .name(performance.getName())
                .price(performance.getPrice())
                .round(performance.getRound())
                .type(convertToName(performance.getType()))
                .startDate(convertToReadableFormat(performance.getStartDate()))
                .seatTotal(seatInfoList.size())
                .seatAvailable(caculateAvailableSeat(seatInfoList))
                .seatInfoList(convertToSeatInfoList(seatInfoList))
                .build();
    }

    private static List<SeatInfo> convertToSeatInfoList(List<PerformanceSeatInfo> seatInfoList) {
        return seatInfoList.stream()
                .map(seatInfo -> SeatInfo.builder()
                        .gate(seatInfo.getGate())
                        .line(seatInfo.getLine())
                        .seat(seatInfo.getSeat())
                        .isReserved((seatInfo.getIsReserve().toString()))
                        .build())
                .toList();
    }

    private static int caculateAvailableSeat(List<PerformanceSeatInfo> seatInfoList) {
        return (int) seatInfoList.stream()
                .filter(seatInfo -> seatInfo.getIsReserve().equals(AVAILABLE))
                .count();
    }

}
