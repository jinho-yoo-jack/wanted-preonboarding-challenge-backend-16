package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("query")
@RequiredArgsConstructor
public class QueryController {
    private final TicketSeller ticketSeller;

    // 공연 및 전시 정보 조회(목록, 상세)
    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<Performance>>> getAllPerformanceInfoList(@RequestParam(value="reserve_status", required=false) String reserveStatus) {
        return ticketSeller.getAllPerformanceInfoList(reserveStatus);
    }

}
