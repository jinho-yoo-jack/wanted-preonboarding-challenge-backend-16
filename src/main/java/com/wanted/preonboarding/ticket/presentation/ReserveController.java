package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;
    /*
    TODO 비즈니스 요구사항:
        예약 조회 시스템
        Request Message: 고객의 이름, 휴대 전화
        Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
        공연 및 전시 정보 조회(목록, 상세 조회)
        Request Message: 예매 가능 여부
        Response Message: 예매 가능한 공연 리스트(정보: 공연명, 회차, 시작 일시, 예매 가능 여부)
        예약 가능 알림 서비스
        특정 공연에 대해서 취소 건이 발생하는 경우, 알림 신청을 해놓은 고객에게 취소된 예약이 있다는 사실을 알리는 알림 서비스
        Send Message: 공연ID, 공연명, 회차, 시작 일시 예매 가능한 좌석 정보
    */

    /*
     예약 시스템
        Request Message: 고객의 이름, 휴대 전화, 결제 가능한 금액(잔고), 예약을 원하는 공연 또는 전시회ID, 회차, 좌석 정보
        Response Message: 예매가 완료된 공연의 정보(회차, 공연명, 좌석정보, 공연ID) + 예매자 정보(이름, 연락처)
        특이사항: 예약 결제 시, 다양한 할인 정책이 적용될 수 있음.
        예제 -
        {
            "performanceId": "2b941b0d-b5f2-11ee-9614-0242ac120002",
            "reservationName": "유진호",
            "reservationPhoneNumber": "010-1234-1234",
            "reservationStatus": "reserve",
            "amount": 200000,
            "round": 1,
            "line": "A",
            "seat": 1
        }
    */
    @PostMapping("/")
    public ResponseEntity<?> reservation(@RequestBody ReserveInfo reserveInfo){
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseHandler.builder().statusCode(HttpStatus.OK)
                        .data(ticketSeller.reserve(reserveInfo))
                        .message("Success")
                        .build());
    }

}
