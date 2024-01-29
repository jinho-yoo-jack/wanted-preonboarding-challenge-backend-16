package com.wanted.preonboarding.ticket.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanted.preonboarding.ticket.TestHelper;
import com.wanted.preonboarding.ticket.domain.dto.request.RequestNotification;
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

import static com.wanted.preonboarding.ticket.TestHelper.convertToJSONString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Rollback
@SpringBootTest
@AutoConfigureMockMvc
class NotificationTest {

    @Autowired
    private TestHelper testHelper;

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
                executeTest("알림 설정 성공", caseA, status().isOk()),
                executeTest("알림 설정 실패 - 존재하지 않는 공연", caseB, status().is4xxClientError())
        );
    }

    private DynamicTest executeTest(
            String testName,
            RequestNotification requestData,
            ResultMatcher expectedStatus
    ) throws JsonProcessingException {
        return testHelper.testApiEndpoint(
                testName,
                post("/api/v1/notification/set")
                        .contentType("application/json")
                        .content(convertToJSONString(requestData)),
                expectedStatus
        );
    }
}
