package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.aop.advice.exception.PerformanceNotFountException;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import com.wanted.preonboarding.ticketing.domain.dto.request.CreateAlarmRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.CancelReservationResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.CreateAlarmResponse;
import com.wanted.preonboarding.ticketing.domain.entity.Alarm;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.repository.AlarmRepository;
import com.wanted.preonboarding.ticketing.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final PerformanceRepository performanceRepository;

    public CreateAlarmResponse createAlarm(CreateAlarmRequest createAlarmRequest) {
        Performance performance = performanceRepository.findById(createAlarmRequest.getPerformanceId())
                .orElseThrow(() -> new PerformanceNotFountException(ErrorCode.NOT_FOUND_PERFORMANCE));
        Alarm alarm = saveAlarm(createAlarmRequest, performance);

        return alarm.toCreateAlarmResponse();
    }

    private Alarm saveAlarm(CreateAlarmRequest createAlarmRequest, Performance performance) {
        Alarm alarm = createAlarmRequest.from(performance);

        return alarmRepository.save(alarm);
    }

    public List<CancelReservationResponse> sendAlarm(PerformanceSeatInfo performanceSeatInfo) {
        List<Alarm> alarms = alarmRepository.findAllByPerformance(performanceSeatInfo.getPerformance());

        return alarms.stream().map(alarm -> alarm.toCancelReservationResponse(performanceSeatInfo))
                .collect(Collectors.toList());
    }
}
