package com.wanted.preonboarding.ticketing.service.alarm;

import com.wanted.preonboarding.ticketing.domain.dto.AlarmInfo;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;

public interface AlarmSender {
    void sendAlarm(AlarmInfo alarmInfo, PerformanceSeatInfo performanceSeatInfo);
}
