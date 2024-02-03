package com.wanted.preonboarding.ticket.application.notification;

import static com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType.CONCERT;
import static com.wanted.preonboarding.ticket.domain.performance.model.ReserveState.ENABLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.wanted.preonboarding.ticket.domain.notification.Notification;
import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.notification.NotificationRepository;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.dto.response.alarm.NotificationResponse;
import com.wanted.preonboarding.ticket.exception.notfound.NotFoundException;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest
class NotificationServiceTest {

    @Autowired
    PerformanceNotificationRegister alarmService;

    @Autowired
    PerformanceRepository performanceRepository;

    @Autowired
    NotificationRepository alarmRepository;

    @BeforeEach
    void setUp() {
        alarmRepository.deleteAllInBatch();
        performanceRepository.deleteAllInBatch();
    }

    @DisplayName("알림 등록 대상이 존재한다면 True를 반환한다.")
    @Test
    void isTargetExist_true() {
        // given
        final String performanceId = savePerformance().toString();

        // when
        boolean targetExist = alarmService.isTargetExist(performanceId);

        // then
        assertThat(targetExist).isTrue();
    }

    @DisplayName("알림 등록 대상이 존재하지 않는다면 false를 반환한다.")
    @Test
    void isTargetExist_false() {
        // given
        final String invalidId = UUID.randomUUID().toString();

        // when
        boolean targetExist = alarmService.isTargetExist(invalidId);

        // then
        assertThat(targetExist).isFalse();
    }

    @DisplayName("알람 등록을 할 수 있다.")
    @Test
    void post_success() {
        // given
        final String performanceId = savePerformance().toString();
        final String name = "기우";
        final String phone = "01012345678";

        // when
        NotificationResponse response = alarmService.register(performanceId, name, phone);

        // then
        Notification alarm = alarmRepository.findById(response.id()).orElseThrow();
        assertThat(alarm.getName()).isEqualTo(name);
        assertThat(alarm.getPhoneNumber()).isEqualTo(phone);
    }

    @DisplayName("알람 등록 대상을 찾을 수 없다면 알람을 등록할 수 없다.")
    @Test
    void post_fail_with_invalid_target() {
        // given
        final String invalidId = UUID.randomUUID().toString();
        final String name = "기우";
        final String phone = "01012345678";

        // when & then
        assertThatThrownBy(() -> alarmService.register(invalidId, name, phone))
            .isInstanceOf(NotFoundException.class);
    }

    @DisplayName("동일한 공연에 대해 알람을 중복으로 등록할 수 없다.")
    @Test
    void post_fail_duplicate_same_performance() {
        // given
        final String invalidId = savePerformance().toString();
        final String name = "기우";
        final String phone = "01012345678";

        alarmService.register(invalidId, name, phone);

        // when & then
        assertThatThrownBy(() -> alarmService.register(invalidId, name, phone))
            .isInstanceOf(DataIntegrityViolationException.class);
    }

    private UUID savePerformance() {
        LocalDateTime startDate = LocalDateTime.of(2024, 12, 31, 19,0);
        final Performance performance = Performance.builder()
            .name("Hola")
            .price(100_000)
            .round(1)
            .type(CONCERT)
            .startDate(startDate)
            .isReserve(ENABLE)
            .build();
        return performanceRepository.save(performance).getId();
    }
}