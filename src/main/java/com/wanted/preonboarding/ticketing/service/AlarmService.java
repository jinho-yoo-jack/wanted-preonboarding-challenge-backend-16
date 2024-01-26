package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.domain.dto.request.CreateAlarmRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.CreateAlarmResponse;
import com.wanted.preonboarding.ticketing.domain.entity.Alarm;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.repository.AlarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AlarmService {
    private final AlarmRepository alarmRepository;

    private final PerformanceService performanceService;

    @Transactional
    public CreateAlarmResponse createAlarm(CreateAlarmRequest createAlarmRequest) {
        Performance performance = performanceService.findPerformance(createAlarmRequest.getPerformanceId());
        Alarm alarm = saveAlarm(createAlarmRequest, performance);

        return alarm.toCreateAlarmResponse();
    }

    private Alarm saveAlarm(CreateAlarmRequest createAlarmRequest, Performance performance) {
        Alarm alarm = createAlarmRequest.from(performance);

        return alarmRepository.save(alarm);
    }
}
