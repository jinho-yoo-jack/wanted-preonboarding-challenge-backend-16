package com.wanted.preonboarding.ticketing.event;

import com.wanted.preonboarding.ticketing.service.dto.AlarmInfo;

import java.util.List;

public record CancelReservationEvent(List<AlarmInfo> alarmInfos, Long performanceSeatInfoId) {
}
