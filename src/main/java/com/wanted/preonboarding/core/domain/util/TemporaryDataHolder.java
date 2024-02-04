package com.wanted.preonboarding.core.domain.util;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Component
public class TemporaryDataHolder {
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @PostConstruct
    public void init()
    {
        List<Performance> performances = performanceRepository.findByIsReserve("enable");
        List<Integer> rounds = Arrays.asList(1, 2);
        List<Integer> seats = Arrays.asList(1, 2, 3);
        List<Character> lines = Arrays.asList('A', 'B', 'C');
        if(performanceSeatInfoRepository.findAll().isEmpty())
        {
            for (Performance performance : performances)
            {
                for (Integer round : rounds)
                {
                    for (Integer seat : seats)
                    {
                        for (Character line : lines)
                        {
                            PerformanceSeatInfo performanceSeatInfo = PerformanceSeatInfo.builder()
                                    .performanceId(performance.getId())
                                    .seatInfo(SeatInfo.builder().round(round).seat(seat).line(line).gate(1).build())
                                    .isReserve("enable")
                                    .build();

                            performanceSeatInfoRepository.save(performanceSeatInfo);
                        }
                    }
                }
            }
        }
    }
}