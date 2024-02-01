package com.wanted.preonboarding.ticket.presentation.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.wanted.preonboarding.ticket.domain.dto.SmsResponse;
import com.wanted.preonboarding.ticket.domain.dto.ReservePossibleAlarmCustomerInfoDto;
import com.wanted.preonboarding.ticket.domain.dto.ResponseDto;
import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import com.wanted.preonboarding.ticket.global.exception.ResultCode;
import com.wanted.preonboarding.ticket.global.exception.ServiceException;
import com.wanted.preonboarding.ticket.presentation.service.AlarmSmsService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

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
