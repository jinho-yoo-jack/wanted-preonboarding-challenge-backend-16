package com.wanted.preonboarding.ticket.application.notification;

import com.wanted.preonboarding.ticket.dto.request.notification.SimpleTarget;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Slf4j
@Primary
@RequiredArgsConstructor
@Service
public class AwsSnsNotificationSender implements NotificationSender {

    // 알림 전송
    @Override
    public void send(final String message, final Collection<SimpleTarget> target) {
        // target 에 대한 정보를 바탕 으로 message 전송
        target.stream()
            .forEach(t -> log.info("send message :: {}", message));
    }
}
