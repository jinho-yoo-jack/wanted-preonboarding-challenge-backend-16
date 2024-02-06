package com.wanted.preonboarding.ticket.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.application.dto.request.CreateReserveServiceRequest;
import com.wanted.preonboarding.ticket.application.dto.request.FindReserveServiceRequest;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
import com.wanted.preonboarding.ticket.application.exception.AlreadyReservedStateException;
import com.wanted.preonboarding.ticket.application.validator.ReservationValidator;
import com.wanted.preonboarding.ticket.presentation.dto.request.CancelReservationRequest;
import com.wanted.preonboarding.ticket.presentation.dto.request.CreateReserveInfoRequest;
import com.wanted.preonboarding.ticket.presentation.dto.request.FindReserveInfoRequest;
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
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
                .gate(1)
                .round(1)
                .line('a')
                .seat(1)
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
                .andExpect(jsonPath("$.data.gate").value(response.gate()))
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

    @DisplayName("고객의 예약 정보를 조회 할 수 있다.")
    @Test
    void findReservationInfoWithClient() throws Exception {
        // given
        String reservationName = "원티드";
        String reservationPhoneNumber = "010-0000-1111";
        String performanceId = UUID.randomUUID().toString();

        ReserveResponse response1 = ReserveResponse.builder()
                .performanceId(performanceId)
                .performanceName("영웅")
                .round(1)
                .line('a')
                .seat(1)
                .reservationName("원티드")
                .reservationPhoneNumber("010-0000-1111")
                .build();

        ReserveResponse response2 = ReserveResponse.builder()
                .performanceId(performanceId)
                .performanceName("영웅")
                .round(1)
                .line('a')
                .seat(2)
                .reservationName("원티드")
                .reservationPhoneNumber("010-0000-1111")
                .build();

        List<ReserveResponse> response = List.of(response1, response2);

        given(reservationService.findReserve(any(FindReserveServiceRequest.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(get("/api/v1/reserves")
                        .contentType(MediaType.APPLICATION_JSON)
                        .queryParam("reservationName", reservationName)
                        .queryParam("reservationPhoneNumber", reservationPhoneNumber))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data[0].performanceId").value(response.get(0).performanceId()))
                .andExpect(jsonPath("$.data[0].performanceName").value(response.get(0).performanceName()))
                .andExpect(jsonPath("$.data[0].round").value(response.get(0).round()))
                .andExpect(jsonPath("$.data[0].line").value(String.valueOf(response.get(0).line())))
                .andExpect(jsonPath("$.data[0].seat").value(response.get(0).seat()))
                .andExpect(jsonPath("$.data[0].reservationName").value(response.get(0).reservationName()))
                .andExpect(jsonPath("$.data[0].reservationPhoneNumber").value(response.get(0).reservationPhoneNumber()))
                .andExpect(jsonPath("$.data[1].performanceId").value(response.get(1).performanceId()))
                .andExpect(jsonPath("$.data[1].performanceName").value(response.get(1).performanceName()))
                .andExpect(jsonPath("$.data[1].round").value(response.get(1).round()))
                .andExpect(jsonPath("$.data[1].line").value(String.valueOf(response.get(1).line())))
                .andExpect(jsonPath("$.data[1].seat").value(response.get(1).seat()))
                .andExpect(jsonPath("$.data[1].reservationName").value(response.get(1).reservationName()))
                .andExpect(jsonPath("$.data[1].reservationPhoneNumber").value(response.get(1).reservationPhoneNumber()))
                .andExpect(jsonPath("$.serverTime").exists());
    }

    @DisplayName("등록되어 있는 예약을 취소 할 수 있다.")
    @Test
    void cancelReservation() throws Exception {
        // given
        Long reservationId = 1L;
        Long userId = 1L;

        CancelReservationRequest request = CancelReservationRequest.builder()
                .userId(userId)
                .build();

        doNothing().when(reservationService).cancel(any(Long.class), any(Long.class));

        // when & then
        mockMvc.perform(post("/api/v1/reserves/{reservationId}/cancel", reservationId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(200))
                .andExpect(jsonPath("$.serverTime").exists());
    }
}