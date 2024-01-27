package com.wanted.preonboarding.ticket.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.application.dto.request.CreateReserveServiceRequest;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
import com.wanted.preonboarding.ticket.application.exception.AlreadyReservedStateException;
import com.wanted.preonboarding.ticket.application.validator.ReservationValidator;
import com.wanted.preonboarding.ticket.presentation.dto.request.CreateReserveInfoRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReserveController.class)
class ReserveControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @DisplayName("예약이 가능한 공연을 예약 할 수 있다.")
    @Test
    void reservePerformance() throws Exception {
        // given
        Long userId = 1L;
        Long performanceSeatInfoId = 1L;

        CreateReserveInfoRequest request = CreateReserveInfoRequest.builder()
                .userId(userId)
                .performanceSeatInfoId(performanceSeatInfoId)
                .build();

        ReserveResponse response = ReserveResponse.builder()
                .performanceId(UUID.randomUUID().toString())
                .performanceName("영웅")
                .round(1)
                .line('a')
                .reservationName("원티드")
                .reservationPhoneNumber("010-0000-1111")
                .build();

        given(reservationService.reserve(any(CreateReserveServiceRequest.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(post("/api/v1/reserves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.performanceId").value(response.performanceId()))
                .andExpect(jsonPath("$.data.performanceName").value(response.performanceName()))
                .andExpect(jsonPath("$.data.round").value(response.round()))
                .andExpect(jsonPath("$.data.line").value(String.valueOf(response.line())))
                .andExpect(jsonPath("$.data.seat").value(response.seat()))
                .andExpect(jsonPath("$.data.reservationName").value(response.reservationName()))
                .andExpect(jsonPath("$.data.reservationPhoneNumber").value(response.reservationPhoneNumber()))
                .andExpect(jsonPath("$.serverTime").exists());
    }

    @DisplayName("예약이 되어 있는 공연은 예약을 할 수 없다.")
    @Test
    void reservePerformanceWithAlreadyReserved() throws Exception {
        // given
        Long userId = 1L;
        Long performanceSeatInfoId = 1L;
        UUID performanceId = UUID.randomUUID();

        CreateReserveInfoRequest request = CreateReserveInfoRequest.builder()
                .userId(userId)
                .performanceSeatInfoId(performanceSeatInfoId)
                .build();

        given(reservationService.reserve(any(CreateReserveServiceRequest.class)))
                .willThrow(new AlreadyReservedStateException(
                        String.format(ReservationValidator.ALREADY_RESERVED_PERFORMANCE_MESSAGE_FORMAT, performanceId)));

        // when & then

        mockMvc.perform(post("/api/v1/reserves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.statusCode").value(400))
                .andExpect(jsonPath("$.message").value(
                        String.format(ReservationValidator.ALREADY_RESERVED_PERFORMANCE_MESSAGE_FORMAT, performanceId)))
                .andExpect(jsonPath("$.serverTime").exists());
    }
}