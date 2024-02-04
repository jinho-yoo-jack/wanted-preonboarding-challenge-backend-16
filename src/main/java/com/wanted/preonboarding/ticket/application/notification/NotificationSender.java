package com.wanted.preonboarding.ticket.application.notification;

import com.wanted.preonboarding.ticket.dto.request.notification.SimpleTarget;
import java.util.Collection;

// 알림 전송자
public interface NotificationSender {
    void send(String message, Collection<SimpleTarget> target);
}
