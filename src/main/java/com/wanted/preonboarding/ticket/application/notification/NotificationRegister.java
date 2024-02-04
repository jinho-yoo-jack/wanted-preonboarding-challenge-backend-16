package com.wanted.preonboarding.ticket.application.notification;

import com.wanted.preonboarding.ticket.dto.response.notification.NotificationRegisterResponse;

public interface NotificationRegister {

    // 알람 등록 주체가 존재하는지 확인
    boolean isTargetExist(String targetId);

    // 공연 & 전시 알람 등록
    NotificationRegisterResponse register(String targetId, String name, String phone);
}
