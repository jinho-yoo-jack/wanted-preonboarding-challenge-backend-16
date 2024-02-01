package com.wanted.preonboarding.ticket.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.push.dto.AlarmVO;
import com.wanted.preonboarding.push.service.PushService;
import com.wanted.preonboarding.ticket.domain.dto.AlarmInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceDetail;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.ReserveNotice;
import com.wanted.preonboarding.ticket.domain.entity.User;
import com.wanted.preonboarding.ticket.domain.mapper.ReservationMapping;
import com.wanted.preonboarding.ticket.infrastructure.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final PerformanceInfoRepository performanceInfoRepository;
    private final ReservationRepository reservationRepository;
    private final ReserveNotiRepository reserveNotiRepository;
    private final UserRepository userRepository;
    private final PushService pushService;
    private final ObjectMapper mapper;

    public ResponseEntity<ResponseHandler<List<Performance>>> getAllPerformanceInfoList(String reserveStatus) {

        try {
            // 전체 조회
            if(StringUtils.isEmpty(reserveStatus)) {
                return ResponseEntity
                        .ok()
                        .body(ResponseHandler.<List<Performance>>builder()
                                .message("Success")
                                .statusCode(HttpStatus.OK)
                                .data(performanceRepository.findAllAsc())
                                .build());
            } else {
                // 상세 조회
                return ResponseEntity
                        .ok()
                        .body(ResponseHandler.<List<Performance>>builder()
                                .message("Success")
                                .statusCode(HttpStatus.OK)
                                .data(performanceRepository.findByIsReserve(reserveStatus))
                                .build());
            }

        } catch (Exception e) {
            log.error("ERROR getAllPerformanceInfoList : {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @Transactional
    public ResponseEntity<ResponseHandler<ReserveInfo>> reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());

        try {
            Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);
            String enableReserve = info.getIsReserve();
            reserveInfo.setPerformanceName(info.getName());

            if (enableReserve.equalsIgnoreCase("enable")) {

                User user = userRepository.findById(reserveInfo.getReservationName())
                        .orElseThrow(EntityNotFoundException::new);

                int amount = user.getTotalAmount();
                int price = discount("reserve", info.getPrice());
                if (amount >= price) {
                    // 1. 결제
                    user.setTotalAmount(amount-price);
                    userRepository.save(user);

                    // 2. 예매 진행
                    reservationRepository.save(Reservation.from(reserveInfo));

                    // 3. 예매 완료
                    performanceInfoRepository.updateIsReserve(reserveInfo.getPerformanceId(), reserveInfo.getSeat(), "disable");

                    // 4. 자리 만석 -> 공연 예약 Update
                    if(!performanceInfoRepository.existsByPerformanceIdAndIsReserve(reserveInfo.getPerformanceId(), "enable")) {
                        performanceRepository.updateIsReserve(reserveInfo.getPerformanceId(), "disable");
                    }

                    return ResponseEntity
                            .ok()
                            .body(ResponseHandler.<ReserveInfo>builder()
                                    .message("Success")
                                    .statusCode(HttpStatus.OK)
                                    .data(reserveInfo)
                                    .build());
                }
            }

            return ResponseEntity
                    .ok()
                    .body(ResponseHandler.<ReserveInfo>builder()
                            .message("Success")
                            .statusCode(HttpStatus.OK)
                            .data(reserveInfo)
                            .build());

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<ResponseHandler<List<ReservationMapping>>> getReserveInfo(String userName, String phoneNumber) {
       return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<ReservationMapping>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(reservationRepository.findByNameAndPhoneNumber(userName, phoneNumber))
                        .build()
                );
    }

    @Transactional
    public ResponseEntity<ResponseHandler<List<ReservationMapping>>> cancel(ReserveInfo reserveInfo) {
        log.info("cancel ID => {}", reserveInfo.getPerformanceId());

        try {
            Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
                    .orElseThrow(EntityNotFoundException::new);
            User user = userRepository.findById(reserveInfo.getReservationName())
                    .orElseThrow(EntityNotFoundException::new);

            int price = discount("reserve", info.getPrice());
            int amount = user.getTotalAmount();

            // 1. 좌석 예약 가능
            performanceInfoRepository.updateIsReserve(reserveInfo.getPerformanceId(), reserveInfo.getSeat(), "enable");

            // 2. 공연 예약 가능
            performanceRepository.updateIsReserve(reserveInfo.getPerformanceId(), "enable");

            // 3. 예약 정보 삭제
            reservationRepository.deleteByPerformanceIdAndSeat(reserveInfo.getPerformanceId(), reserveInfo.getSeat());

            // 4. 예약 할인금만큼 환불
            user.setTotalAmount(amount + price);

            // 5. 웹 소켓 PUSH
            sendByAlarmMessage(reserveInfo);

            return ResponseEntity
                    .ok()
                    .body(ResponseHandler.<List<ReservationMapping>>builder()
                            .message("Success")
                            .statusCode(HttpStatus.OK)
                            .data(reservationRepository.findByNameAndPhoneNumber(user.getUserName(), user.getPhoneNumber()))
                            .build()
                    );

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public ResponseEntity<ResponseHandler<String>> setAlarmIsReserve(AlarmInfo alarmInfo) {
        try {
            log.info("setAlarmIsReserve {}", mapper.writeValueAsString(alarmInfo));

            reserveNotiRepository.save(ReserveNotice.from(alarmInfo));

            return ResponseEntity
                    .ok()
                    .body(ResponseHandler.<String>builder()
                            .message("Success")
                            .statusCode(HttpStatus.OK)
                            .data("")
                            .build());
        } catch (Exception e) {
            log.error("ERROR setAlarmIsReserve : {}", e.getMessage(), e);
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ResponseHandler.<String>builder()
                            .message("Internal Server Error")
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR)
                            .data("")
                            .build());
        }
    }

    // 공연 취소 시 알림 PUSH
    public void sendByAlarmMessage(ReserveInfo cancelInfo) throws Exception {

        /*
         info 가져오는 부분 미구현..
         */
        PerformanceDetail info = performanceInfoRepository.findByPerformanceAndSeat(cancelInfo.getPerformanceId(), cancelInfo.getRound(), cancelInfo.getSeat());
        AlarmVO pushData = AlarmVO.builder()
                                  .performanceId(info.getPerformanceId())
                                  .performanceName(info.getName())
                                  .round(info.getRound())
                                  .seat(info.getSeat())
                                  .startDate(info.getStartDate())
                                  .build();

        for(String user : reserveNotiRepository.findByUserName(cancelInfo.getPerformanceId(), "Y")) {
            pushService.webSocketSendByUser(user, pushData);
        }
    }

    // 할인
    public int discount(String discountName, int price) {

        return switch (discountName) {
            case "reserve" -> (int)(price * 0.95); // 예약 -> 5% 할인
            default -> price;
        };
    }

}
