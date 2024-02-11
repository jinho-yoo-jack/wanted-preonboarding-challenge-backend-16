package com.wanted.preonboarding.ticket.domain.service;

import com.wanted.preonboarding.core.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.core.exception.PerformanceSeatInfoNotFoundException;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceId;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationRequestDto;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;


    public ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto) {
        Performance performance = performanceRepository.findById(new PerformanceId(reservationRequestDto.getPerformanceId(), reservationRequestDto.getRound())).orElseThrow(PerformanceNotFoundException::new);
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findByPerformanceAndLineAndSeat(performance, reservationRequestDto.getLine(), reservationRequestDto.getSeat())
                .orElseThrow(PerformanceSeatInfoNotFoundException::new);

        Reservation reservation = Reservation.newInstance(performance, performanceSeatInfo, reservationRequestDto.getReservationHolderName(), reservationRequestDto.getReservationHolderPhoneNumber());
        return ReservationResponseDto.newInstance(reservation);

    }
}
