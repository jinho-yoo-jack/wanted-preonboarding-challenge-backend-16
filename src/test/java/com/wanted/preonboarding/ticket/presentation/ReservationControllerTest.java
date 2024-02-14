package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.util.JsonUtil;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationFindRequest;
import com.wanted.preonboarding.ticket.exception.ErrorCode;
import com.wanted.preonboarding.ticket.exception.ReservationNotFoundException;
import com.wanted.preonboarding.ticket.exception.SeatAlreadyReservedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.UUID;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
class ReservationControllerTest {
    @Mock
    private ReservationService reservationService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext ctx;
    private final String baseUrl = "/reserve";
    private final String cancelUrl = baseUrl + "/cancel";

    @BeforeEach
    public void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    @DisplayName("공연 예약 성공")
    public void reserveSuccess() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post(baseUrl)
                                .content(JsonUtil.toJsonString(ReservationCreateRequest.builder()
                                        .performanceId(UUID.fromString("2a4d95dc-c77e-11ee-88ff-0242ac130002"))
                                        .reservationName("name")
                                        .reservationPhoneNumber("010-1234-1233")
                                        .balance(210000)
                                        .round(1)
                                        .seat(4)
                                        .line('A')
                                        .build()))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("공연 예약 실패")
    public void reserveFail() throws Exception {
        ReservationCreateRequest request = ReservationCreateRequest.builder()
                .performanceId(UUID.fromString("2a4d95dc-c77e-11ee-88ff-0242ac130002"))
                .reservationName("name")
                .reservationPhoneNumber("010-1234-1233")
                .balance(210000)
                .round(1)
                .seat(3)
                .line('A')
                .build();

        Mockito.doThrow(new SeatAlreadyReservedException(ErrorCode.SEAT_ALREADY_RESERVED))
                .when(reservationService).reserve(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(baseUrl)
                                .content(JsonUtil.toJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("공연 예약 조회 성공")
    public void findReservationSuccess() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get(baseUrl)
                                .param("reservationName", "JH")
                                .param("reservationPhoneNumber", "010-1234-4567"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("공연 예약 조회 실패")
    public void findReservationFail() throws Exception {
        ReservationFindRequest request = ReservationFindRequest.builder()
                .reservationName("HH")
                .reservationPhoneNumber("010-1111-2223")
                .build();
        Mockito.doThrow(new ReservationNotFoundException(ErrorCode.RESERVATION_NOT_FOUND))
                .when(reservationService).findReservation(request);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(baseUrl)
                                .param("reservationName", "HH")
                                .param("reservationPhoneNumber", "010-1111-2223"))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }

    @Test
    @DisplayName("공연 예약 취소 성공")
    public void cancelReservationSuccess() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.delete(cancelUrl)
                                .content(JsonUtil.toJsonString(ReservationCancelRequest.builder()
                                        .reservationId(20)
                                        .build()))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
