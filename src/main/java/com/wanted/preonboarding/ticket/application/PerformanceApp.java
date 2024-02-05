package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.CreatedPerformanceRequestDto;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class PerformanceApp {
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatRepository performanceSeatRepository;

    public void create(CreatedPerformanceRequestDto requestDto){
        Performance performance = Performance.of(requestDto);
        List<String> stringSeatInfo = requestDto.generateCombinations();
        List<PerformanceSeatInfo> performanceSeatInfoList = new ArrayList<>();

        for (String stringSeat : stringSeatInfo) {
            PerformanceSeatInfo seatInfo = PerformanceSeatInfo.convertSeatInfo(stringSeat);
            seatInfo.setPerformance(performance);
            performanceSeatInfoList.add(seatInfo);
        }
        performanceRepository.save(performance);
        performanceSeatRepository.saveAll(performanceSeatInfoList);
    }

    public PerformanceInfo getDetail(String name) {
        Performance performance = performanceRepository.findByName(name)
                .orElseThrow(EntityNotFoundException::new);
        return PerformanceInfo.of(performance);
    }


}
