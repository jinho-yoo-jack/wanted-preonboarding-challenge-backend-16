package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.AlarmInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.mapper.ReservationMapping;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;

    // 예약 시스템
    @PostMapping
    public ResponseEntity<ResponseHandler<ReserveInfo>> reservation(@RequestBody ReserveInfo reserveInfo) {
        return ticketSeller.reserve(reserveInfo);
    }
    // 예약 조회 시스템
    @GetMapping
    public ResponseEntity<ResponseHandler<List<ReservationMapping>>> getReserveInfo(@RequestParam(value="customer_name") String customerName,
                                                                                    @RequestParam(value="phone_number") String phoneNumber) {
        return ticketSeller.getReserveInfo(customerName, phoneNumber);
    };

    // 예약 취소
    @PostMapping("/cancel")
    public ResponseEntity<ResponseHandler<List<ReservationMapping>>> cancelReservation(@RequestBody ReserveInfo reserveInfo) {

        return ticketSeller.cancel(reserveInfo);
    }

    // 공연 예약 가능 알림 설정
    @PostMapping("/alarm")
    public ResponseEntity<ResponseHandler<String>> setAlarmIsReserve(@RequestBody AlarmInfo alarmInfo) {
        return ticketSeller.setAlarmIsReserve(alarmInfo);
    }
}
