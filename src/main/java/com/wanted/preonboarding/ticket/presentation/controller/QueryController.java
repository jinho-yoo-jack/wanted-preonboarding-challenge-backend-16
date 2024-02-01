package com.wanted.preonboarding.ticket.presentation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import com.wanted.preonboarding.ticket.global.exception.InvalidInputException;
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

    @GetMapping("/all/performance")
    public ResponseEntity<ResponseHandler<List<ResponsePerformanceInfo>>> getAllPerformanceInfoList() {
        System.out.println("getAllPerformanceInfoList");
        return ResponseEntity
            .ok()
            .body(ResponseHandler.<List<ResponsePerformanceInfo>>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(ticketSeller.getAllPerformanceInfoList())
                .build()
            );
    }




//    @GetMapping("/reserve/info")
//    public ResponseEntity<ResponseDto> getReserveInfo(@RequestBody RequestReserveQueryDto dto) {
//        log.info("ReserveController getReserveInfo");
//        ResponseDto responseDto = new ResponseDto();
//        try {
//            responseDto.setResponse("success");
//            responseDto.setMessage("예약 조회를 성공적으로 완료했습니다.");
//            ResponseReserveQueryDto responseQueryDto = ticketSeller.getReserveInfoDetail(dto);
//            responseDto.setData(ReserveSystemDto.builder()
//                    .responseQueryDto(responseQueryDto)
//                    .build());
//            return ResponseEntity.ok(responseDto);
//        } catch (InvalidInputException e) {
//            responseDto.setResponse("failed");
//            responseDto.setMessage("예약 정보가 없습니다.");
//            responseDto.setData(e.toString());
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseDto);
//        } catch (Exception e) {
//            responseDto.setResponse("failed");
//            responseDto.setMessage("예약 정보를 불러오는 중 오류가 발생했습니다.");
//            responseDto.setData(e.toString());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseDto);
//        }
//    }



//    @PostMapping("/customer/performance-seat/new")
//    public ResponseEntity<BaseResDto> sendMessagePerformanceCancelCameout(@RequestBody ReservePossibleAlarmCustomerInfoDto dto) {
//        BaseResDto baseResDto = alarmSmsService.performanceCancelCameout(dto);
//        return ResponseEntity.ok(baseResDto);
//    }


    @GetMapping("/reserve/info2")
    public ResponseEntity<BaseResDto> getReserveInfo2(@RequestBody RequestReserveQueryDto dto) {
        log.info("ReserveController getReserveInfo");
        BaseResDto baseResDto = ticketSeller.getReserveInfoDetail(dto);
//        baseResDto.setResultData(ReserveSystemDto.builder()
//                .responseQueryDto(responseQueryDto)
//                .build());
        return ResponseEntity.ok(baseResDto);
    }

}
