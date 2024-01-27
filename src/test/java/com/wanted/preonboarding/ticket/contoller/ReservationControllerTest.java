package com.wanted.preonboarding.ticket.contoller;

import static com.wanted.preonboarding.ticket.exception.ExceptionMessage.NOT_FOUND_PERFORMANCE;
import static com.wanted.preonboarding.ticket.exception.ExceptionMessage.NOT_FOUND_SEAT_INFO;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;
import com.wanted.preonboarding.ticket.exception.EntityNotFound;
import com.wanted.preonboarding.ticket.exception.InsufficientPaymentException;
import com.wanted.preonboarding.ticket.service.ReservationService;
import com.wanted.preonboarding.ticket.service.dto.request.ReservationCheckRequestDto;
import com.wanted.preonboarding.ticket.service.dto.request.ReservationRequestDto;
import com.wanted.preonboarding.ticket.service.dto.response.ReservationCheckResponseDto;
import com.wanted.preonboarding.ticket.service.dto.response.ReservationResponseDto;

@WebMvcTest(controllers = ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @Test
    void 예약_성공() throws Exception {
        // given
        final Performance performance = Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .name("공연 이름")
                .price(10000)
                .status(ReservationStatus.AVAILABLE)
                .start_date(LocalDateTime.of(2024, 2, 1, 10, 0))
                .build();

        final ReservationResponseDto responseDto = ReservationResponseDto.builder()
                .performanceId(performance.getId())
                .performanceName(performance.getName())
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .amount(0)
                .round(1)
                .line('A')
                .seat(1)
                .build();

        given(reservationService.reserve(any()))
                .willReturn(responseDto);

        // when
        // then
        final ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .amount(10000)
                .round(1)
                .line('A')
                .seat(1)
                .build();

        mockMvc.perform(post("/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andExpectAll(
                        jsonPath("$.data.performanceId").value(performance.getId().toString()),
                        jsonPath("$.data.performanceName").value(performance.getName()),
                        jsonPath("$.data.reservationName").value("홍길동"),
                        jsonPath("$.data.reservationPhoneNumber").value("010-1234-5678"),
                        jsonPath("$.data.amount").value(0),
                        jsonPath("$.data.round").value(1),
                        jsonPath("$.data.line").value("A"),
                        jsonPath("$.data.seat").value(1))
                .andDo(print());
    }

    @Test
    void 공연이_존재하지_않으면_예약_실패() throws Exception {
        // given
        given(reservationService.reserve(any()))
                .willThrow(new EntityNotFound(NOT_FOUND_PERFORMANCE.getMessage()));

        // when
        // then
        final ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .amount(10000)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        mockMvc.perform(post("/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void 좌석이_존재하지_않으면_예약_실패() throws Exception {
        // given
        given(reservationService.reserve(any()))
                .willThrow(new EntityNotFound(NOT_FOUND_SEAT_INFO.getMessage()));

        // when
        // then
        final ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .amount(10000)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        mockMvc.perform(post("/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @ParameterizedTest
    @ValueSource(ints = {9999, 9000, 6000, 4000})
    void 잔고가_부족하면_예약_실패(final int amount) throws Exception {
        // given
        final Performance performance = Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .name("공연 이름")
                .price(10000)
                .status(ReservationStatus.AVAILABLE)
                .start_date(LocalDateTime.of(2024, 2, 1, 10, 0))
                .build();

        given(reservationService.reserve(
                        argThat(requestDto -> requestDto.amount() < performance.getPrice())))
                .willThrow(InsufficientPaymentException.class);

        // when
        // then
        final ReservationRequestDto requestDto = ReservationRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .amount(amount)
                .round(1)
                .line('B')
                .seat(1)
                .build();

        mockMvc.perform(post("/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void 예약_조회() throws Exception {
        // given
        given(reservationService.check(any()))
                .willReturn(List.of(ReservationCheckResponseDto.builder()
                        .reservationName("홍길동")
                        .reservationPhoneNumber("010-1234-5678")
                        .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                        .performanceName("공연 이름")
                        .round(1)
                        .line('A')
                        .seat(1)
                        .build()));

        // when
        // then
        final ReservationCheckRequestDto request = ReservationCheckRequestDto.builder()
                .reservationName("홍길동")
                .reservationPhoneNumber("010-1234-5678")
                .build();
        mockMvc.perform(get("/reserve")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
