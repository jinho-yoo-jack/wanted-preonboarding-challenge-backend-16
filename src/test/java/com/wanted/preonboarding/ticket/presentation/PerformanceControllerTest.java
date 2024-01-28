package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.support.DateConverter;
import com.wanted.preonboarding.ticket.support.PerformanceFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PerformanceController.class)
class PerformanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PerformanceService performanceService;

    @DisplayName("공연의 상세 정보를 확인 할 수 있다.")
    @Test
    void findOne() throws Exception {
        // given
        UUID performanceId = UUID.randomUUID();
        Performance performance = PerformanceFactory.create(performanceId);
        PerformanceResponse response = PerformanceResponse.from(performance);

        given(performanceService.findOne(any(UUID.class)))
                .willReturn(response);

        // when
        mockMvc.perform(get("/api/v1/performances/{performanceId}", performanceId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.performanceName").value(response.performanceName()))
                .andExpect(jsonPath("$.data.round").value(response.round()))
                .andExpect(jsonPath("$.data.performanceDate").value(
                        DateConverter.convertLocalDateTimeToString(response.performanceDate())))
                .andExpect(jsonPath("$.data.isReserve").value(response.isReserve()))
                .andExpect(jsonPath("$.serverTime").exists());
    }

    @DisplayName("예약가능한 공연의 정보를 확인 할 수 있다.")
    @Test
    void findAvailablePerformances() throws Exception {
        // given
        Performance performance1 = PerformanceFactory.create();
        performance1 = PerformanceFactory.changeReservationState(performance1, Performance.ENABLE);
        PerformanceResponse response1 = PerformanceResponse.from(performance1);

        Performance performance2 = PerformanceFactory.create();
        performance2 = PerformanceFactory.changeReservationState(performance2, Performance.ENABLE);
        PerformanceResponse response2 = PerformanceResponse.from(performance2);

        given(performanceService.findPerformances(any(String.class)))
                .willReturn(List.of(response1, response2));

        // when
        mockMvc.perform(get("/api/v1/performances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("isReserve", Performance.ENABLE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].performanceName").value(response1.performanceName()))
                .andExpect(jsonPath("$.data[0].round").value(response1.round()))
                .andExpect(jsonPath("$.data[0].performanceDate").value(
                        DateConverter.convertLocalDateTimeToString(response1.performanceDate())))
                .andExpect(jsonPath("$.data[0].isReserve").value(response1.isReserve()))
                .andExpect(jsonPath("$.data[1].performanceName").value(response2.performanceName()))
                .andExpect(jsonPath("$.data[1].round").value(response2.round()))
                .andExpect(jsonPath("$.data[1].performanceDate").value(
                        DateConverter.convertLocalDateTimeToString(response2.performanceDate())))
                .andExpect(jsonPath("$.data[1].isReserve").value(response2.isReserve()))
                .andExpect(jsonPath("$.serverTime").exists());
    }

    @DisplayName("예약 불가능한 공연의 정보를 확인 할 수 있다.")
    @Test
    void findUnavailablePerformances() throws Exception {
        // given
        Performance performance = PerformanceFactory.create();
        PerformanceResponse response = PerformanceResponse.from(performance);

        given(performanceService.findPerformances(any(String.class)))
                .willReturn(List.of(response));

        // when
        mockMvc.perform(get("/api/v1/performances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("isReserve", Performance.DISABLE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].performanceName").value(response.performanceName()))
                .andExpect(jsonPath("$.data[0].round").value(response.round()))
                .andExpect(jsonPath("$.data[0].performanceDate").value(
                        DateConverter.convertLocalDateTimeToString(response.performanceDate())))
                .andExpect(jsonPath("$.data[0].isReserve").value(response.isReserve()))
                .andExpect(jsonPath("$.serverTime").exists());
    }
}