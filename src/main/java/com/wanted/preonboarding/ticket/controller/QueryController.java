package com.wanted.preonboarding.ticket.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReservationInfoRequest;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("query")
@RequiredArgsConstructor
public class QueryController {
    private final TicketSeller ticketSeller;

    /*
        공연 및 전시 정보 조회(목록, 상세 조회)
        Request Message: 예매 가능 여부
        Response Message: 예매 가능한 공연 리스트(정보: 공연명, 회차, 시작 일시, 예매 가능 여부)
     */
    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList() {
        log.info("getAllPerformanceInfoList");
        return ResponseEntity.ok()
                .body(ResponseHandler.<List<PerformanceInfo>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.getAllPerformanceInfoList())
                        .build()
                );
    }

    /*
       예약 조회 시스템
       Request Message: 고객의 이름, 휴대 전화
       Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
    */
    @PostMapping("/reservations")
    public ResponseEntity<ResponseHandler<List<ReserveInfo>>> getReservationInfo(
            @Valid @RequestBody ReservationInfoRequest reservationInfoRequest
    ) {
        log.info("getAllPerformanceInfoList");
        return ResponseEntity.ok()
                .body(ResponseHandler.<List<ReserveInfo>>builder()
                        .message("Success")
                        .data(ticketSeller.getReservationInfos(reservationInfoRequest))
                        .statusCode(HttpStatus.OK)
                        .build());
    }
}
