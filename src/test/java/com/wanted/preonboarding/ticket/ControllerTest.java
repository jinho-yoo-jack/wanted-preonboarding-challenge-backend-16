package com.wanted.preonboarding.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.ticket.domain.dto.request.RequestNotification;
import com.wanted.preonboarding.ticket.domain.dto.request.RequestReservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Rollback
@SpringBootTest
@AutoConfigureMockMvc
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private static String convertToJSONString(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }


    @TestFactory
    @Transactional
    @DisplayName(("공연/전시 정보 목록 & 상세 조회 테스트"))
    Stream<DynamicTest> dynamicTestForPerformance() {
        // Given
        UUID validId = UUID.fromString("2e1347ac-b556-11ee-9508-0242ac130002");
        int validRound = 1;
        int invalidRound = 100;
        String successCase = validId + "/" + validRound;
        String failCase = validId + "/" + invalidRound;

        // When & Then
        return Stream.of(
                testApiEndpoint("공연/전시 정보 목록 조회 성공",
                        get("/api/v1/performance/list"),
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isArray()),
                testApiEndpoint("공연/전시 정보 상세 조회 성공",
                        get("/api/v1/performance/detail/" + successCase),
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isNotEmpty()),
                testApiEndpoint("공연/전시 정보 상세 조회 실패 - 존재하지 않는 공연",
                        get("/api/v1/performance/detail/" + failCase),
                        status().is4xxClientError(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isEmpty())
        );
    }

    @TestFactory
    @Transactional
    @DisplayName("예약 등록 기능 테스트")
    Stream<DynamicTest> dynamicTestForReservation() throws Exception {
        // Given
        UUID id = UUID.fromString("2e1347ac-b556-11ee-9508-0242ac130002");
        RequestReservation caseA = new RequestReservation("홍길동", "010-1234-1234", 300000, id, 1, 'A', 1);
        RequestReservation caseB = new RequestReservation("홍길동", "010-1234-1234", 30000, id, 1, 'A', 2);
        RequestReservation caseC = new RequestReservation("홍길동", "010-1234-1234", 300000, id, 1, 'B', 3);

        // When & Then
        return Stream.of(
                testApiEndpoint("예약 성공",
                        post("/api/v1/reservation/process")
                                .contentType("application/json")
                                .content(convertToJSONString(caseA)),
                        status().isCreated(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isNotEmpty()),
                testApiEndpoint("예약 실패 - 이미 예약된 좌석",
                        post("/api/v1/reservation/process")
                                .contentType("application/json")
                                .content(convertToJSONString(caseA)),
                        status().is4xxClientError(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isEmpty()),
                testApiEndpoint("예약 실패 - 잔고 부족",
                        post("/api/v1/reservation/process")
                                .contentType("application/json")
                                .content(convertToJSONString(caseB)),
                        status().is4xxClientError(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isEmpty()),
                testApiEndpoint("예약 실패 - 예약 불가능한 좌석",
                        post("/api/v1/reservation/process")
                                .contentType("application/json")
                                .content(convertToJSONString(caseC)),
                        status().is4xxClientError(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isEmpty())
        );
    }

    @TestFactory
    @Transactional
    @DisplayName("예약 조회 기능 테스트")
    Stream<DynamicTest> dynamicTestForReservationInfo() {
        // Given
        String[] successCase = {"홍길동", "010-1234-1234"};
        String[] failCaseA = {"김철수", "010-1234-1234"};
        String[] failCaseB = {"Peter", "1234"};


        // When & Then
        return Stream.of(
                testApiEndpoint("예약 조회 성공",
                        get("/api/v1/reservation/info")
                                .param("name", successCase[0])
                                .param("phone_number", successCase[1]),
                        status().isOk(),
                        jsonPath("$.data").isNotEmpty()),
                testApiEndpoint("예약 조회 실패 - 존재하지 않는 예약자",
                        get("/api/v1/reservation/info")
                                .param("name", failCaseA[0])
                                .param("phone_number", failCaseA[1]),
                        status().is4xxClientError(),
                        jsonPath("$.data").isEmpty()),
                testApiEndpoint("예약 조회 실패 - 잘못된 요청",
                        get("/api/v1/reservation/info")
                                .param("name", failCaseB[0])
                                .param("phone_number", failCaseB[1]),
                        status().is4xxClientError(),
                        jsonPath("$.data").isEmpty())
        );
    }

    @TestFactory
    @Transactional
    @DisplayName("예약 취소 기능 테스트")
    Stream<DynamicTest> dynamicTestForReservationCancel() {
        // Given
        int successCase = 10;
        int failCase = 100;

        // When & Then
        return Stream.of(
                testApiEndpoint("예약 취소 성공",
                        delete("/api/v1/reservation/cancel/" + successCase),
                        status().isOk(),
                        jsonPath("$.data").isEmpty()),
                testApiEndpoint("예약 취소 실패 - 존재하지 않는 예약",
                        delete("/api/v1/reservation/cancel/" + failCase),
                        status().is4xxClientError(),
                        jsonPath("$.data").isEmpty())
        );
    }

    @TestFactory
    @Transactional
    @DisplayName("알림 설정 기능 테스트")
    Stream<DynamicTest> dynamicTestForNotification() throws Exception {
        // Given
        UUID validId = UUID.fromString("2e1347ac-b556-11ee-9508-0242ac130002");
        UUID invalidId = UUID.fromString("1e1111ac-b556-11ee-9508-0242ac130003");
        RequestNotification caseA = new RequestNotification(validId, 1, "test@test.com");
        RequestNotification caseB = new RequestNotification(invalidId, 1, "test@test.com");

        // When & Then
        return Stream.of(
                testApiEndpoint("알림 설정 성공",
                        post("/api/v1/notification/set")
                                .contentType("application/json")
                                .content(convertToJSONString(caseA)),
                        status().isOk()),
                testApiEndpoint("알림 설정 실패 - 존재하지 않는 공연",
                        post("/api/v1/notification/set")
                                .contentType("application/json")
                                .content(convertToJSONString(caseB)),
                        status().is4xxClientError(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isEmpty())
        );
    }

    protected DynamicTest testApiEndpoint(String testName, RequestBuilder requestBuilder, ResultMatcher... matchers) {
        return DynamicTest.dynamicTest(testName, () -> {
            ResultActions result = mockMvc.perform(requestBuilder);
            for (ResultMatcher matcher : matchers) {
                result.andExpect(matcher);
            }
        });
    }


}
