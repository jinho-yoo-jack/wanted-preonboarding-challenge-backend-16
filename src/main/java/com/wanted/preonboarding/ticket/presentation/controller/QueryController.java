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

    /**
     * 공연 및 전시 정보 조회(목록, 상세 조회)
     * Request Message: 예매 가능 여부
     * Response Message: 예매 가능한 공연 리스트(정보: 공연명, 회차, 시작 일시, 예매 가능 여부)
     * @return
     */
    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<ResponsePerformanceInfo>>> readAllPerformances() {
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<ResponsePerformanceInfo>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(ticketSeller.readAllPerformances())
                .build());
    }

}
