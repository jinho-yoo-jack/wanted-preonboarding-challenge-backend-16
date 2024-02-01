package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.domain.dto.NotificationInfo;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.NotificationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final PerformanceRepository performanceRepository;

    @Transactional
    public NotificationInfo subscribe(Long userId, UUID performanceId) {
        Notification notification = notificationRepository.save(Notification.builder()
                .userId(userId)
                .performanceId(performanceId)
                .build());
        return NotificationInfo.of(notification);
    }

    public void notifyCancellation(UUID performanceId) {
        List<Notification> usersToNotify = notificationRepository.findByPerformanceId(performanceId);

        for (Notification user : usersToNotify) {
            sendMessage(user, performanceId);
        }
    }

    private void sendMessage(Notification user, UUID performanceId) {
        Performance performance = performanceRepository.findById(performanceId).orElseThrow();
        LocalDateTime now = LocalDateTime.now();

        if(user.getNotifiedAt() != null) {
            long timeBetween = ChronoUnit.MINUTES.between(user.getNotifiedAt(), now);
            if (timeBetween >= 0 && timeBetween <= 10) return;
        }

        String performanceName = performance.getName();
        Date startDate = performance.getStart_date();
        user.setNotifiedAt(now);
        notificationRepository.save(user);

        // userId 를 통해 유저를 찾아 보내는 것으로 가정
        log.info("[취소표 발생 알림] {} {} 의 취소 좌석이 나왔습니다. 지금 원티드에서 확인하세요!", performanceName, startDate);
    }
}
