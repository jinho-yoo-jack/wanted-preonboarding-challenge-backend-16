package com.wanted.preonboarding.ticket.application.notification;

// 알림 전송자
public interface NotificationSender {
    <M, T> NotificationInfo<M, T> send(M message, T target);
}
