package com.wanted.preonboarding.ticket.application.notification;

import com.wanted.preonboarding.ticket.domain.notification.Notification;
import com.wanted.preonboarding.ticket.domain.notification.NotificationRepository;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.dto.response.notification.NotificationRegisterResponse;
import com.wanted.preonboarding.ticket.exception.notfound.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PerformanceNotificationRegister implements NotificationRegister {

    private final PerformanceRepository performanceRepository;
    private final NotificationRepository alarmRepository;

    @Override
    public boolean isTargetExist(final String targetId) {
        UUID performanceId = UUID.fromString(targetId);
        return performanceRepository.findNameById(performanceId).isPresent();
    }

    @Transactional
    @Override
    public NotificationRegisterResponse register(final String targetId, final String name, final String phone) {
        if (!isTargetExist(targetId)) throw new NotFoundException("알람 대상을 찾을 수 없습니다.");

        Notification notification = Notification.builder()
            .targetId(targetId)
            .name(name)
            .phoneNumber(phone)
            .build();

        return NotificationRegisterResponse.of(alarmRepository.save(notification));
    }
}
