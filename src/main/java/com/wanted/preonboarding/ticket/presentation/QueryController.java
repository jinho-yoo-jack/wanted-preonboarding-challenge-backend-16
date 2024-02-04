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
@RequestMapping("performance")
@RequiredArgsConstructor
public class QueryController {
    private final TicketSeller ticketSeller;

    @GetMapping("/all")
    public ResponseEntity<List<PerformanceInfo>> getAllPerformanceInfoList() {
        List<PerformanceInfo> allPerformanceInfoList = ticketSeller.getAllPerformanceInfoList();
        return new ResponseEntity<>(allPerformanceInfoList, HttpStatus.OK);
    }


    @GetMapping("/{performanceId}")
    public ResponseEntity<PerformanceInfo> getPerformanceById(@PathVariable(value = "performanceId") String performanceId) {
        PerformanceInfo performanceById = ticketSeller.getPerformanceById(performanceId);
        return new ResponseEntity<>(performanceById, HttpStatus.OK);
    }


    @PostMapping
    public ResponseEntity<PerformanceInfo> putPerformanceInfo(@RequestBody CreatePerformance createPerformance) {
        PerformanceInfo performanceInfo = ticketSeller.addPerformance(createPerformance);
        return new ResponseEntity<>(performanceInfo, HttpStatus.OK);

    }

    @PostMapping("/specific")
    public ResponseEntity<UUID> getPerformanceId(@RequestBody PerformanceIdRequest performanceIdRequest) {
        UUID performanceId = ticketSeller.getPerformanceId(performanceIdRequest);
        return new ResponseEntity<>(performanceId, HttpStatus.OK);
    }


}
