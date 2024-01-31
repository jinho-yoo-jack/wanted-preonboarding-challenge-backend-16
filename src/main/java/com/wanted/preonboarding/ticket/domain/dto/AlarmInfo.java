package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class AlarmInfo {
    private Integer alarmId;
    private PerformanceInfo performanceInfo;
    private UserInfo userInfo;

    public static AlarmInfo of (Alarm alarm, PerformanceInfo performanceInfo, UserInfo userInfo) {
        return AlarmInfo.builder()
                .alarmId(alarm.getId())
                .performanceInfo(performanceInfo)
                .userInfo(userInfo)
                .build();
    }
}
