package com.wanted.preonboarding.ticket.application.notification;

import com.wanted.preonboarding.ticket.domain.notification.Notification;
import com.wanted.preonboarding.ticket.domain.notification.NotificationRepository;
import com.wanted.preonboarding.ticket.dto.request.notification.NotificationHolder;
import com.wanted.preonboarding.ticket.dto.request.notification.SimpleTarget;
import com.wanted.preonboarding.ticket.support.Retry;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Service
public class NotificationService {

    private final NotificationSender notificationSender;
    private final NotificationRepository notificationRepository;

    // COMMIT 후 알림 전송 (비동기)
    @Retry
    @Async
    @TransactionalEventListener
    public void sendNotification(final NotificationHolder holder) {
        // 알림 메시지 생성
        String message = holder.getMessage();

        // 알림 대상 조회
        String targetId = holder.getTargetId();
        List<Notification> notifications = notificationRepository.findAllByTargetId(targetId);
        List<SimpleTarget> target = notifications.stream()
            .map(SimpleTarget::of)
            .toList();

        // 알림 전송
        notificationSender.send(message, target);
    }

}
