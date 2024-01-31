package com.wanted.preonboarding.ticket.controller;

import com.wanted.preonboarding.ticket.domain.dto.AlarmInfo;
import com.wanted.preonboarding.ticket.service.AlarmService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/alarm")
public class AlarmController {
    private final AlarmService alarmService;

    /** 특정 공연에 대한 취소 알림 등록 **/
    @PostMapping(value = "")
    public ResponseEntity<AlarmInfo> registAlarm(@RequestBody AlarmInfo alarmInfo) {
        return ResponseEntity
                .ok()
                .body(alarmService.registAlarm(alarmInfo));
    }

}
