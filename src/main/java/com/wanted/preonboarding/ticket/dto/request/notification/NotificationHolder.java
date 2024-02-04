package com.wanted.preonboarding.ticket.dto.request.notification;

/*
* getMessage() -> 알림 메시지
* getTargetId() -> 알림 targetId (ex - performanceId)
* */
public interface NotificationHolder {
    String getMessage();
    String getTargetId();
}
