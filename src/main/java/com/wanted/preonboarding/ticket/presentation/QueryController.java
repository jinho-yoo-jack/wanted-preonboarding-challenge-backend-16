package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ticket.TicketSellerService;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
    private final TicketSellerService ticketSellerService;

    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<PerformanceInfoResponse>>> getAllPerformanceInfoList(
            @RequestParam(name="able",defaultValue="all") String able
    )
    {
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<PerformanceInfoResponse>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(ticketSellerService.getAllPerformanceInfoList(able))
                .build()
            );
    }
}
