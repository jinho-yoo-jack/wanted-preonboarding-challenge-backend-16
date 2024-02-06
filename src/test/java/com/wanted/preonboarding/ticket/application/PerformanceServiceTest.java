package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.dto.AwaitInfo;
import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import com.wanted.preonboarding.ticket.application.validator.AwaitValidator;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.AwaitRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.support.PerformanceFactory;
import com.wanted.preonboarding.user.User;
import com.wanted.preonboarding.user.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
public class PerformanceServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PerformanceRepository performanceRepository;

    @Autowired
    private AwaitRepository awaitRepository;

    @Autowired
    private PerformanceService performanceService;

    @DisplayName("하나의 공연을 조회 할 수 있다.")
    @Test
    void findOne() {
        // given
        Performance performance = PerformanceFactory.create(null);
        Performance savedPerformance = performanceRepository.save(performance);

        // when
        PerformanceResponse response = performanceService.findOne(savedPerformance.getId());

        // then
        assertThat(response).isNotNull();
    }

    @DisplayName("예약이 가능한 공연을 조회 할 수 있다.")
    @Test
    void findAvailablePerformances() {
        // when
        List<PerformanceResponse> performances = performanceService.findPerformances(Performance.ENABLE);

        // then
        assertThat(performances).isNotEmpty();
        assertThat(performances).extracting("isReserve").containsExactlyInAnyOrder(Performance.ENABLE);
    }

    @DisplayName("예약이 불가능한 공연을 조회 할 수 있다.")
    @Test
    void findUnavailablePerformances() {
        // when
        List<PerformanceResponse> performances = performanceService.findPerformances(Performance.DISABLE);

        // then
        assertThat(performances).isNotEmpty();
        assertThat(performances).extracting("isReserve").containsExactlyInAnyOrder(Performance.DISABLE);
    }

    @DisplayName("공연의 알람을 등록 할 수 있다.")
    @Test
    void submitAwaitAlarmPerformance() {
        // given
        Long userId = 1L;
        Performance performance = PerformanceFactory.create();
        Performance savedPerformance = performanceRepository.save(performance);

        UUID performanceId = savedPerformance.getId();

        User findUser = userRepository.findById(userId).get();
        AwaitInfo awaitInfo = AwaitInfo.of(performanceId, findUser);

        // when
        performanceService.await(performanceId, userId);
        List<AwaitInfo> awaitInfos = awaitRepository.findByPerformanceId(performanceId);

        // then
        assertThat(awaitInfos.contains(awaitInfo)).isTrue();
    }

    @DisplayName("공연의 알람 등록 시 존재하지 않는 공연이라면 예외가 발생한다.")
    @Test
    void submitAwaitAlarmPerformanceWithNotFound() {
        // given
        Long userId = 1L;
        UUID performanceId = UUID.randomUUID();

        // when & then
        assertThatThrownBy(() -> performanceService.await(performanceId, userId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage(String.format(AwaitValidator.AWAIT_ERROR_MESSAGE_FORMAT, performanceId));
    }
}
