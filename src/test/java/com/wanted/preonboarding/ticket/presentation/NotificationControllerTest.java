package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.util.JsonUtil;
import com.wanted.preonboarding.ticket.application.NotificationService;
import com.wanted.preonboarding.ticket.domain.dto.request.NotificationCreateRequest;
import com.wanted.preonboarding.ticket.exception.ErrorCode;
import com.wanted.preonboarding.ticket.exception.PerformanceNotFoundException;
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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
class NotificationControllerTest {
    @Mock
    NotificationService notificationService;
    @Autowired
    private MockMvc mockMvc;
    private final String baseUrl = "/notification";
    @Autowired
    private WebApplicationContext ctx;

    @BeforeEach
    void init() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(MockMvcResultHandlers.print())
                .build();
    }

    @Test
    @DisplayName("알림 저장 성공")
    public void saveSuccessfully() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.post(baseUrl)
                                .content(JsonUtil.toJsonString(NotificationCreateRequest.builder()
                                        .name("JH")
                                        .phoneNumber("010-1111-2222")
                                        .email("test@check.com")
                                        .performanceId(UUID.fromString("2a4d95dc-c77e-11ee-88ff-0242ac130002"))
                                        .build()))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    @DisplayName("알림 저장 실패 (공연 ID가 잘못되었을 때)")
    public void saveNotificationFail() throws Exception {
        NotificationCreateRequest request = NotificationCreateRequest.builder()
                .name("JH")
                .phoneNumber("010-1111-2222")
                .email("test@check.com")
                .performanceId(UUID.fromString("2a4d95dc-c77e-11ee-88ff-0242ac130006")) //잘못된 공연ID
                .build();

        Mockito.doThrow(new PerformanceNotFoundException(ErrorCode.PERFORMANCE_NOT_FOUND))
                .when(notificationService).saveNotification(request);

        mockMvc.perform(
                        MockMvcRequestBuilders.post(baseUrl)
                                .content(JsonUtil.toJsonString(request))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

    @Test
    @DisplayName("알림 전체 조회")
    public void findAllNotification() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get(baseUrl)
                                .param("performanceId", "2a4d95dc-c77e-11ee-88ff-0242ac130002"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
    }
}
