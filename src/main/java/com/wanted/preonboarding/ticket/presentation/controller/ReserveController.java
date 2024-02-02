package com.wanted.preonboarding.ticket.presentation.controller;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.aop.dto.BaseResDto;
import com.wanted.preonboarding.ticket.aop.ResultCode;
import com.wanted.preonboarding.ticket.aop.exception.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;

    @PostMapping
    public ResponseEntity<BaseResDto> reservation(@RequestBody ReserveInfo info) {
        log.info("ReserveController reservation");
        if(ticketSeller.reserve(info)) {
            return ResponseEntity.ok(ticketSeller.getPerformanceInfoDetail(info));
        } else {
            throw new ServiceException(ResultCode.RESERVE_NOT_VALID);
        }
    }
}
