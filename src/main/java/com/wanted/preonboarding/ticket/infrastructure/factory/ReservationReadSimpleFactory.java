package com.wanted.preonboarding.ticket.infrastructure.factory;

import com.wanted.preonboarding.ticket.domain.entity.DiscountInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceId;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.reader.DiscountInfoReader;
import com.wanted.preonboarding.ticket.infrastructure.reader.PerformanceReader;
import com.wanted.preonboarding.ticket.infrastructure.reader.PerformanceSeatInfoReader;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationReadDto;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationReadSimpleFactory {

    private final PerformanceReader performanceReader;
    private final PerformanceSeatInfoReader performanceSeatInfoReader;
    private final DiscountInfoReader discountInfoReader;

    public ReservationReadDto getReservationReadDto(ReservationRequestDto reservationRequestDto) {
        Performance performance = performanceReader.getOpeningPerformance(new PerformanceId(reservationRequestDto.getPerformanceId(), reservationRequestDto.getRound()));
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoReader.getOpeningSeatInfo(performance, reservationRequestDto.getLine(), reservationRequestDto.getSeat());
        List<DiscountInfo> discountInfos = discountInfoReader.getActiveDiscountInfos();
        return ReservationReadDto.builder()
                .performance(performance)
                .performanceSeatInfo(performanceSeatInfo)
                .discountInfos(discountInfos)
                .requestDiscountTypes(reservationRequestDto.getDiscounts())
                .reservationHolderName(reservationRequestDto.getReservationHolderName())
                .reservationHolderPhoneNumber(reservationRequestDto.getReservationHolderPhoneNumber())
                .balance(reservationRequestDto.getBalance())
                .build();
    }
}
