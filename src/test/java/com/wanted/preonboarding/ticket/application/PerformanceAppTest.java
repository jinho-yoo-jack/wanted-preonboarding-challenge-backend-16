package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.CreatePerformanceRequestDto;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    CreatePerformanceRequestDto requestDto;
    Performance performance ;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        requestDto = CreatePerformanceRequestDto.builder()
                        .performanceName("testPerformanceName")
                        .price(10000)
                        .round(1)
                        .type(1)
                        .start_date(Date.valueOf("2024-02-06"))
                        .lineRange("A").seatRange("1~3")
                        .build();

        performance = Performance.of(requestDto);

    }

    @Test
    @DisplayName("createdPerformance")
    void create() throws Exception {
        List<String> actualSeatInfo = requestDto.generateCombinations();
        List<PerformanceSeatInfo> actualSeatList = new ArrayList<>();
        for (String seatInfo : actualSeatInfo) {
            actualSeatList.add(PerformanceSeatInfo.convertSeatInfo(seatInfo));
        }
        List<PerformanceSeatInfo> expectedSeatList = new ArrayList<>();
        expectedSeatList.add(PerformanceSeatInfo.convertSeatInfo("A1"));
        expectedSeatList.add(PerformanceSeatInfo.convertSeatInfo("A2"));
        expectedSeatList.add(PerformanceSeatInfo.convertSeatInfo("A3"));

        performanceApp.create(requestDto);

        assertEquals(expectedSeatList.size(), actualSeatList.size());

    }

    @Test
    @DisplayName("getDetail")
    void getDetail() throws Exception {
        //given
        when(performanceRepository.findByName("testPerformanceName")).thenReturn(Optional.of(performance));

        //when
        PerformanceInfo performanceInfo = performanceApp.getDetail("testPerformanceName");

        //then
        assertEquals(performanceInfo.getPerformanceName(),"testPerformanceName");
    }

}