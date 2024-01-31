package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.CreatePerformance;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceIdRequest;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("query")
@RequiredArgsConstructor
public class QueryController {
    private final TicketSeller ticketSeller;


    //TODO: 예약 가능한 공연 목록 조회
    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList() {
        System.out.println("getAllPerformanceInfoList");

        List<PerformanceInfo> allPerformanceInfoList = ticketSeller.getAllPerformanceInfoList();
        allPerformanceInfoList.stream().forEach(e -> e.getPerformanceId());

        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<PerformanceInfo>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.getAllPerformanceInfoList())
                        .build()
                );
    }


    @PostMapping("/performance")
    public ResponseEntity<Boolean> putPerformanceInfo(@RequestBody CreatePerformance createPerformance) {
        boolean result = ticketSeller.addPerformance(createPerformance);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    //TODO: 예약가능 공연 상세정보조회
    @PostMapping("/performanceId")
    public Performance getPerformanceId(@RequestBody PerformanceIdRequest performanceIdRequest) {
        Performance performance = ticketSeller.getPerformanceId(performanceIdRequest);
        return performance;
    }


}
