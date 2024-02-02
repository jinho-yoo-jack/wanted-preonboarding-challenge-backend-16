package com.wanted.preonboarding.ticket.application.alarm;

import com.wanted.preonboarding.ticket.dto.response.alarm.AlarmResponse;

public interface AlarmService {

    // 알람 등록 주체가 존재하는지 확인
    boolean isTargetExist(String targetId);

    // 공연 & 전시 알람 등록
    AlarmResponse post(String targetId, String name, String phone);
}
