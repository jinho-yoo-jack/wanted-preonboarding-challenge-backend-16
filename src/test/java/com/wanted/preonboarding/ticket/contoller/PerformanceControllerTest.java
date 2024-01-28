package com.wanted.preonboarding.ticket.contoller;

import static com.wanted.preonboarding.ticket.domain.entity.ReservationStatus.AVAILABLE;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;
import com.wanted.preonboarding.ticket.service.dto.response.PerformanceCheckResponseDto;
import com.wanted.preonboarding.ticket.service.PerformanceService;
import com.wanted.preonboarding.ticket.service.dto.request.PerformanceCheckRequestDto;

@WebMvcTest(controllers = PerformanceController.class)
class PerformanceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PerformanceService performanceService;

    @Test
    void 조회() throws Exception {
        // given
        final Performance performance = Performance.builder()
                .id(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .name("공연 이름")
                .price(10000)
                .round(1)
                .status(ReservationStatus.AVAILABLE)
                .start_date(LocalDateTime.of(2024, 2, 1, 10, 0))
                .build();

        given(performanceService.getPerformances(any()))
                .willReturn(List.of(PerformanceCheckResponseDto.of(performance)));

        // when
        // then
        final PerformanceCheckRequestDto request = new PerformanceCheckRequestDto(AVAILABLE);

        mockMvc.perform(get("/performances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
