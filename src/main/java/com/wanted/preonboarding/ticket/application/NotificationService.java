package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.NotificationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationCreateResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.NotificationFindResponse;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.ticket.infrastructure.repository.NotificationRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final PerformanceRepository performanceRepository;

    // 저장
    @Transactional
    public NotificationCreateResponse saveNotification(NotificationCreateRequest request) {
        log.info("NotificationService.saveNotification");
        Performance performance = findPerformance(request);
        Notification saveNotification = getSaveNotification(request, performance);
        return mapToNotificationCreateResponse(saveNotification);
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public List<NotificationFindResponse> findAllNotification(UUID performanceId) {
        Performance performance = findPerformanceById(performanceId);
        List<Notification> notificationList = findAllNotification(performance);
        return mapToNotificationFindResponseList(notificationList);
    }

    private NotificationCreateResponse mapToNotificationCreateResponse(Notification saveNotification) {
        return saveNotification.toNotificationCreateResponse();
    }

    private List<NotificationFindResponse> mapToNotificationFindResponseList(List<Notification> notificationList) {
        return notificationList.stream().map(Notification::toNotificationFindResponse).toList();
    }

    private List<Notification> findAllNotification(Performance performance) {
        return notificationRepository.findAllByPerformance(performance);
    }

    private Performance findPerformanceById(UUID performanceId) {
        return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new PerformanceNotFoundException("공연 정보가 존재하지 않습니다."));
    }

    private Notification getSaveNotification(NotificationCreateRequest request, Performance performance) {
        return notificationRepository.save(Notification.of(request, performance));
    }

    private Performance findPerformance(NotificationCreateRequest request) {
        return performanceRepository.findById(request.getPerformanceId())
                .orElseThrow(() -> new PerformanceNotFoundException("공연 정보가 존재하지 않습니다."));
    }

}
