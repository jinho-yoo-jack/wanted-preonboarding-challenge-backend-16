package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.config.UserAuth;
import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceDetailInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.SeatInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("query")
@RequiredArgsConstructor
public class QueryController {
    private final TicketSeller ticketSeller;

    /**
     * 공연 및 전시 정보 조회(목록)
     * @return
     */
    @GetMapping(value = "/all/performance")
    public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList() {
        System.out.println("getAllPerformanceInfoList");
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<PerformanceInfo>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(ticketSeller.getAllPerformanceInfoList())
                .build()
            );
    }

    @GetMapping(value = "/{performanceId}")
    public ResponseEntity<ResponseHandler<PerformanceDetailInfo>> getPerformanceDetailInfo(@PathVariable String performanceId){
        PerformanceDetailInfo performanceInfo = ticketSeller.getPerformanceInfoDetailById(performanceId);
        List<SeatInfo> detailInfos = ticketSeller.getPerformanceSeatInfoDetailById(performanceId);

        if(detailInfos.isEmpty()) {
            return ResponseEntity
                .ok()
                .body(ResponseHandler.<PerformanceDetailInfo>builder()
                    .message("Success")
                    .statusCode(HttpStatus.OK)
                    .data(performanceInfo)
                    .build()
                );
        }
        performanceInfo.haveReserveAbleSeat(detailInfos);
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<PerformanceDetailInfo>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(performanceInfo)
                .build()
            );


    }
}
