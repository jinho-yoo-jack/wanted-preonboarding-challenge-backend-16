package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private long totalAmount = 0L;

    @Transactional
    public boolean addPerformance(CreatePerformance createPerformance) {
        UUID uuid = UUID.randomUUID();

        Performance performance = Performance.builder().
                id(uuid)
                .name(createPerformance.getName())
                .price(createPerformance.getPrice())
                .round(createPerformance.getRound())
                .type(createPerformance.getType())
                .startDate(createPerformance.getStart_date())
                .isReserve(createPerformance.getIsReserve())
                .build();

        performanceRepository.save(performance);


        return true;

    }

}
