package com.wanted.preonboarding.ticket.integration;

import com.wanted.preonboarding.ticket.TestHelper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Rollback
@SpringBootTest
@AutoConfigureMockMvc
class PerformanceTest {

    @Autowired
    private TestHelper testHelper;


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
                createPerformanceTest("공연/전시 정보 목록 조회 성공", "/api/v1/performance/list", status().isOk()),
                createPerformanceTest("공연/전시 정보 상세 조회 성공", "/api/v1/performance/detail/" + successCase, status().isOk()),
                createPerformanceTest("공연/전시 정보 상세 조회 실패 - 존재하지 않는 회차", "/api/v1/performance/detail/" + failCase, status().is4xxClientError())
        );
    }

    private DynamicTest createPerformanceTest(String testName, String url, ResultMatcher... expectedStatus) {
        return testHelper.testApiEndpoint(
                testName,
                get(url),
                expectedStatus
        );
    }

}
