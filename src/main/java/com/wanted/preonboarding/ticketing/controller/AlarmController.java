package com.wanted.preonboarding.ticketing.controller;

import com.wanted.preonboarding.ticketing.domain.dto.request.CreateAlarmRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.CreateAlarmResponse;
import com.wanted.preonboarding.ticketing.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @PostMapping("/alarm")
    public ResponseEntity<CreateAlarmResponse> createAlarm(@RequestBody CreateAlarmRequest createAlarmRequest) {
        return ResponseEntity.ok(alarmService.createAlarm(createAlarmRequest));
    }
}
