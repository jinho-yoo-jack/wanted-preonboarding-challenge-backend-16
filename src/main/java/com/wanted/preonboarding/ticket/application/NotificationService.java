package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.NotificationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationCreateResponse;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.ticket.infrastructure.repository.NotificationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final PerformanceRepository performanceRepository;

    public NotificationCreateResponse saveNotification(NotificationCreateRequest request) {
        log.info("NotificationService.saveNotification");
        Performance performance = findPerformance(request);
        Notification saveNotification = getSaveNotification(request, performance);
        return saveNotification.toNotificationCreateResponse();
    }

    private Notification getSaveNotification(NotificationCreateRequest request, Performance performance) {
        return notificationRepository.save(Notification.of(request, performance));
    }

    private Performance findPerformance(NotificationCreateRequest request) {
        return performanceRepository.findById(request.getPerformanceId())
                .orElseThrow(() -> new PerformanceNotFoundException("공연 정보가 존재하지 않습니다."));
    }

}
