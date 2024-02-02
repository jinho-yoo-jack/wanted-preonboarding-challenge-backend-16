package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.alarm.PerformanceAlarmService;
import com.wanted.preonboarding.ticket.dto.request.alarm.PerformanceAlarmRequest;
import com.wanted.preonboarding.ticket.dto.response.alarm.AlarmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/v1/app/alarm")
@RestController
public class AlarmController {

    private final PerformanceAlarmService performanceAlarmService;

    @PostMapping("/performance")
    public ResponseEntity<AlarmResponse> postPerformanceAlarm(@RequestBody PerformanceAlarmRequest request) {
        AlarmResponse result = performanceAlarmService.post(request.performanceId(), request.name(), request.phone());
        return ResponseEntity.ok(result);
    }

}
