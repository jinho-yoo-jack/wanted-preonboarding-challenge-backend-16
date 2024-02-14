package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.NotificationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationCreateResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationFindResponse;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.ticket.infrastructure.repository.NotificationRepository;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
class NotificationServiceTest {
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;
    private final String successPerformanceId = "2a4d95dc-c77e-11ee-88ff-0242ac130002";
    private final String failedPerformanceId = "2a4d95dc-c77e-11ee-88ff-0242ac130004";

    @Test
    @DisplayName("알림 저장 성공")
    public void saveNotificationSuccess() {

        //given
        NotificationCreateRequest request = NotificationCreateRequest.builder()
                .name("JH")
                .phoneNumber("010-1234-4567")
                .email("test@check.com")
                .performanceId(UUID.fromString(successPerformanceId))
                .build();

        // when
        NotificationCreateResponse saveNotification = notificationService.saveNotification(request);
        Notification findNotification = notificationRepository.findById(saveNotification.getId()).get();

        // then
        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(findNotification).isNotNull();
        softAssertions.assertThat(findNotification.getName()).isEqualTo(saveNotification.getName());
        softAssertions.assertThat(findNotification.getPerformance().getId()).isEqualTo(saveNotification.getPerformanceId());
        softAssertions.assertAll();
    }

    @Test
    @DisplayName("알림 저장 실패 (잘못된 공연ID)")
    public void saveNotificationFail() {
        // given
        NotificationCreateRequest request = NotificationCreateRequest.builder()
                .name("JH")
                .phoneNumber("010-1234-4567")
                .email("test@check.com")
                .performanceId(UUID.fromString(failedPerformanceId))
                .build();
        //expected
        Assertions.assertThatThrownBy(() -> notificationService.saveNotification(request))
                .isInstanceOf(PerformanceNotFoundException.class);
    }

    @Test
    @DisplayName("알림 전체 조회 성공")
    public void findAllNotificationSuccess() {
        // given
        NotificationCreateRequest request = NotificationCreateRequest.builder()
                .name("JH")
                .phoneNumber("010-1234-4567")
                .email("test@check.com")
                .performanceId(UUID.fromString(successPerformanceId))
                .build();
        notificationService.saveNotification(request);

        // when
        List<NotificationFindResponse> allNotification = notificationService.findAllNotification(UUID.fromString(successPerformanceId));

        // then
        Assertions.assertThat(allNotification).isNotNull();
    }

    @Test
    @DisplayName("알림 전체 조회 실패 (잘못된 공연ID)")
    public void findAllNotificationFail() {
        // expected
        Assertions.assertThatThrownBy(() ->
                        notificationService.findAllNotification(UUID.fromString(failedPerformanceId)))
                .isInstanceOf(PerformanceNotFoundException.class);
    }
}
