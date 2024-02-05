package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.CreatedPerformanceRequestDto;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import jakarta.persistence.Column;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PerformanceAppTest {


    @InjectMocks
    private PerformanceApp performanceApp;

    @Mock
    private PerformanceSeatRepository performanceSeatRepository;

    @Mock
    private PerformanceRepository performanceRepository;

    @Test
    @DisplayName("createdPerformance")
    void create() throws Exception {
        // Initialize mocks
        MockitoAnnotations.initMocks(this);

        // Mock data
        CreatedPerformanceRequestDto requestDto =
                CreatedPerformanceRequestDto.builder()
                .performanceName("testPerformanceName")
                        .price(10000)
                        .round(1)
                        .type(1)
                        .start_date(Date.valueOf("2024-02-06"))
                        .lineRange("A").seatRange("1~3")
                        .build();

        List<String> actualSeatInfo = requestDto.generateCombinations();
        List<PerformanceSeatInfo> actualSeatList = new ArrayList<>();
        for (String seatInfo : actualSeatInfo) {
            actualSeatList.add(PerformanceSeatInfo.convertSeatInfo(seatInfo));
        }
        //Test Data Create
        List<PerformanceSeatInfo> expectedSeatList = new ArrayList<>();
        expectedSeatList.add(PerformanceSeatInfo.convertSeatInfo("A1"));
        expectedSeatList.add(PerformanceSeatInfo.convertSeatInfo("A2"));
        expectedSeatList.add(PerformanceSeatInfo.convertSeatInfo("A3"));


        performanceApp.create(requestDto);

        assertEquals(expectedSeatList.size(), actualSeatList.size());

        verify(performanceSeatRepository, times(1)).saveAll(anyList());
        verify(performanceRepository, times(1)).save(any(Performance.class));
    }

}