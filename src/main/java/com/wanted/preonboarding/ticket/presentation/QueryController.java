package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.CreatePerformance;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceIdRequest;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("query")
@RequiredArgsConstructor
public class QueryController {
    private final TicketSeller ticketSeller;


    //TODO: 예약 가능한 공연 목록 조회
    @GetMapping("/all/performance")
    public ResponseEntity<List<PerformanceInfo>> getAllPerformanceInfoList() {
        List<PerformanceInfo> allPerformanceInfoList = ticketSeller.getAllPerformanceInfoList();
        return new ResponseEntity<>(allPerformanceInfoList, HttpStatus.OK);
    }


    @PostMapping("/performance")
    public ResponseEntity<PerformanceInfo> putPerformanceInfo(@RequestBody CreatePerformance createPerformance) {
        PerformanceInfo performanceInfo = ticketSeller.addPerformance(createPerformance);
        return new ResponseEntity<>(performanceInfo, HttpStatus.OK);

    }


    //TODO: 예약가능 공연 상세정보조회
    @PostMapping("/specificPerformance")
    public ResponseEntity<UUID> getPerformanceId(@RequestBody PerformanceIdRequest performanceIdRequest) {
        UUID performanceId = ticketSeller.getPerformanceId(performanceIdRequest);
        return new ResponseEntity<>(performanceId, HttpStatus.OK);
    }


}
