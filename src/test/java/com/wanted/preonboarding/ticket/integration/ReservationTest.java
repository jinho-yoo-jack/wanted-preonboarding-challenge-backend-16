package com.wanted.preonboarding.ticket.integration;

import com.wanted.preonboarding.ticket.TestHelper;
import com.wanted.preonboarding.ticket.domain.dto.request.RequestReservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Stream;

import static com.wanted.preonboarding.ticket.TestHelper.convertToJSONString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@Rollback
@SpringBootTest
@AutoConfigureMockMvc
class ReservationTest {

    @Autowired
    private TestHelper testHelper;

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
        String endPoint = "/api/v1/reservation/proceed";
        return Stream.of(
                executeTest("예약 성공",
                        post(endPoint)
                                .contentType("application/json")
                                .content(convertToJSONString(caseA)),
                        status().isCreated(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isNotEmpty()),
                executeTest("예약 실패 - 이미 예약된 좌석",
                        post(endPoint)
                                .contentType("application/json")
                                .content(convertToJSONString(caseA)),
                        status().is4xxClientError(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isEmpty()),
                executeTest("예약 실패 - 잔고 부족",
                        post(endPoint)
                                .contentType("application/json")
                                .content(convertToJSONString(caseB)),
                        status().is4xxClientError(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isEmpty()),
                executeTest("예약 실패 - 예약 불가능한 좌석",
                        post(endPoint)
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
        String endPoint = "/api/v1/reservation/info";
        return Stream.of(
                executeTest("예약 조회 성공",
                        get(endPoint)
                                .param("name", successCase[0])
                                .param("phone_number", successCase[1]),
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isNotEmpty()),
                executeTest("예약 조회 실패 - 존재하지 않는 예약자",
                        get(endPoint)
                                .param("name", failCaseA[0])
                                .param("phone_number", failCaseA[1]),
                        status().is4xxClientError()),
                executeTest("예약 조회 실패 - 잘못된 요청",
                        get(endPoint)
                                .param("name", failCaseB[0])
                                .param("phone_number", failCaseB[1]),
                        status().is4xxClientError())
        );

    }

    @TestFactory
    @Transactional
    @DisplayName("예약 취소 기능 테스트")
    Stream<DynamicTest> dynamicTestForReservationCancel() {
        // Given
        String successCase = "N6Y9DJ";
        String failCase = "AAA111";

        // When & Then
        String endPoint = "/api/v1/reservation/cancel/";
        return Stream.of(
                executeTest("예약 취소 성공",
                        delete(endPoint + successCase),
                        status().isOk()),
                executeTest("예약 취소 실패 - 존재하지 않는 예약",
                        delete(endPoint + failCase),
                        status().is4xxClientError())
        );
    }

    private DynamicTest executeTest(String testName, RequestBuilder requestBuilder, ResultMatcher... expectedStatus) {
        return testHelper.testApiEndpoint(
                testName,
                requestBuilder,
                expectedStatus
        );
    }
}
