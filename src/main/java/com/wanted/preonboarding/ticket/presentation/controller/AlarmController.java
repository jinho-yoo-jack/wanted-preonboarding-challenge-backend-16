package com.wanted.preonboarding.ticket.presentation.controller;


import com.wanted.preonboarding.ticket.domain.dto.request.CreateAlarmPerformanceSeatRequest;
import com.wanted.preonboarding.ticket.presentation.service.AlarmMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {
    private final AlarmMailService alarmMailService;

    @PostMapping("/send")
    public ResponseEntity<Void> sendAlarm(@RequestBody CreateAlarmPerformanceSeatRequest dto) {
        alarmMailService.sendAlarmPerformanceSeat(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/create")
    public ResponseEntity<Void> createAlarm(@RequestBody CreateAlarmPerformanceSeatRequest dto) {
        alarmMailService.createAlarmPerformanceSeat(dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
