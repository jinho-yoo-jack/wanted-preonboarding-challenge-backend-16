package com.wanted.preonboarding.ticket.presentation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.aop.dto.BaseResDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/query")
@RequiredArgsConstructor
public class QueryController {
    private final TicketSeller ticketSeller;

    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<ResponsePerformanceInfo>>> getAllPerformanceInfoList() {
        log.info("QueryController getAllPerformanceInfoList");
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<ResponsePerformanceInfo>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(ticketSeller.getAllPerformanceInfoList())
                .build()
            );
    }


    @GetMapping("/reserve/info")
    public ResponseEntity<BaseResDto> getReserveInfo(@RequestBody RequestReserveQueryDto dto) {
        log.info("QueryController getReserveInfo");
        BaseResDto baseResDto = ticketSeller.getReserveInfoDetail(dto);
        return ResponseEntity.ok(baseResDto);
    }

}
