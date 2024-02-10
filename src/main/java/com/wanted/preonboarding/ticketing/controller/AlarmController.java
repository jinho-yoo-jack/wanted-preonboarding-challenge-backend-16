package com.wanted.preonboarding.ticketing.controller;

import com.wanted.preonboarding.ticketing.controller.request.CreateAlarmRequest;
import com.wanted.preonboarding.ticketing.controller.response.CreateAlarmResponse;
import com.wanted.preonboarding.ticketing.service.alarm.AlarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmService alarmService;

    @PostMapping
    public ResponseEntity<CreateAlarmResponse> createAlarm(@RequestBody @Valid CreateAlarmRequest createAlarmRequest) {
        return ResponseEntity.ok(alarmService.createAlarm(createAlarmRequest));
    }
}
