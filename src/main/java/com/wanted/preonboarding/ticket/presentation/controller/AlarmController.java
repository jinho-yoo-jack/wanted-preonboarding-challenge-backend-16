package com.wanted.preonboarding.ticket.presentation.controller;


import com.wanted.preonboarding.ticket.domain.dto.ReservePossibleAlarmCustomerInfoDto;
import com.wanted.preonboarding.ticket.aop.dto.BaseResDto;
import com.wanted.preonboarding.ticket.presentation.service.AlarmSmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ControllerAdvice
@RestController
@RequestMapping("/alarm")
@RequiredArgsConstructor
public class AlarmController {

    private final AlarmSmsService alarmSmsService;

    @PostMapping("/customer/performance-seat/new")
    public ResponseEntity<BaseResDto> sendMessagePerformanceCancelCameout(@RequestBody ReservePossibleAlarmCustomerInfoDto dto) {
        log.info("AlarmController sendMessagePerformanceCancelCameout");
        BaseResDto baseResDto = alarmSmsService.performanceCancelCameout(dto);
        return ResponseEntity.ok(baseResDto);
    }
}
