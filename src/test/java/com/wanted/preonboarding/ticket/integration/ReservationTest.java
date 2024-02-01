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
        UUID invalidId = UUID.fromString("ffb02517-67fc-4ab8-adb8-a1b521d525ad");
        RequestReservation caseA = new RequestReservation("홍길동", "010-1234-1234", 300000, id, 1, 'A', 1);
        RequestReservation caseB = new RequestReservation("홍길동", "010-1234-1234", 30000, id, 1, 'A', 2);
        RequestReservation caseC = new RequestReservation("홍길동", "010-1234-1234", 300000, id, 1, 'B', 3);
        RequestReservation caseD = new RequestReservation("홍길동", "010-1234-1234", 300000, invalidId, 1, 'A', 1);
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
                        jsonPath("$.data").isEmpty()),
                executeTest("예약 실패 - 예약 불가능한 공연",
                        post(endPoint)
                                .contentType("application/json")
                                .content(convertToJSONString(caseD)),
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
        String successCase = "6SMYHX";
        String failCase = "AAA111";

        // When & Then
        String endPoint = "/api/v1/reservation/detail/";
        return Stream.of(
                executeTest("예약 조회 성공",
                        get(endPoint + successCase),
                        status().isOk(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isNotEmpty()),
                executeTest("예약 조회 실패 - 존재하지 않는 예약",
                        get(endPoint + failCase),
                        status().is4xxClientError(),
                        content().contentType("application/json"),
                        jsonPath("$.data").isEmpty())
        );
    }

    @TestFactory
    @Transactional
    @DisplayName("예약 취소 기능 테스트")
    Stream<DynamicTest> dynamicTestForReservationCancel() {
        // Given
        String successCase = "6SMYHX";
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
