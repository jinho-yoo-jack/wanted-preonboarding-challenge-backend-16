package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.global.exception.InvalidInputException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private long totalAmount = 0L;

    public List<ResponsePerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(ResponsePerformanceInfo::of)
            .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(ReserveInfo info) {
        Optional<Performance> optionalPerformance = performanceRepository.findById(info.getPerformanceId());

        if(!optionalPerformance.isPresent()) {
            throw new InvalidInputException("예약 공연 정보가 없습니다.");
        }

        return PerformanceInfo.of(optionalPerformance.get());
    }

    @Transactional
    public boolean reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);
        String enableReserve = info.getIsReserve();

        if (enableReserve.equalsIgnoreCase("enable")) {
            // 1. 결제
            int price = info.getPrice();
            reserveInfo.setAmount(reserveInfo.getAmount() - price);
            // 2. 예매 진행
            reservationRepository.save(Reservation.of(reserveInfo));
            return true;

        } else {
            return false;
        }
    }

    public ResponseReserveQueryDto getReserveInfoDetail(RequestReserveQueryDto dto) {
        Optional<Reservation> optionalReservation = reservationRepository.findByNameAndPhoneNumber(dto.getReservationName(), dto.getReservationPhoneNumber());

        if(!optionalReservation.isPresent()) {
            throw new InvalidInputException("예약 고객 정보가 없습니다.");
        }

        ResponseReserveQueryDto responseQueryDto = ResponseReserveQueryDto.of(optionalReservation.get());
        Reservation reservation = optionalReservation.get();
        Optional<Performance> optionalPerformance = performanceRepository.findById(reservation.getPerformanceId());
        if(!optionalPerformance.isPresent()) {
            throw new IllegalArgumentException("예약한 공연 정보가 없습니다.");
        }
        String performanceName = optionalPerformance.get().getName();

        responseQueryDto.setPerformanceName(performanceName);

        return responseQueryDto;
    }



    public SendMessagePerformanceSeatInfoDto performanceCancelCameout(ReservePossibleAlarmCustomerInfoDto reserveAlarmCustomerInfoDto) {

        isSendReserveExist(reserveAlarmCustomerInfoDto);

        Optional<PerformanceSeatInfo> optionalPerformanceSeatInfo = performanceSeatInfoRepository.findByUUID(reserveAlarmCustomerInfoDto.getPerformanceId());

        if(!optionalPerformanceSeatInfo.isPresent()) {
            throw new EntityNotFoundException();
        }
        PerformanceSeatInfo performanceSeatInfo = optionalPerformanceSeatInfo.get();


        Optional<Performance> optionalPerformance = performanceRepository.findById(reserveAlarmCustomerInfoDto.getPerformanceId());
        if(!optionalPerformance.isPresent()) {
            throw new EntityNotFoundException();
        }
        Performance performance = optionalPerformance.get();


        SendMessagePerformanceSeatInfoDto sendMessagePerformanceSeatInfoDto = SendMessagePerformanceSeatInfoDto.of(performanceSeatInfo);
        sendMessagePerformanceSeatInfoDto.setPerformanceName(performance.getName());
        sendMessagePerformanceSeatInfoDto.setStartDate(performance.getStart_date());

        //알림 보내기
        sendAlarm(sendMessagePerformanceSeatInfoDto, reserveAlarmCustomerInfoDto);

        return SendMessagePerformanceSeatInfoDto.of(performanceSeatInfo);
    }

    private void isSendReserveExist(ReservePossibleAlarmCustomerInfoDto reservePossibleAlarmCustomerInfoDto) {
        Optional<Reservation> optionalReservation = reservationRepository.findByNameAndPhoneNumber(reservePossibleAlarmCustomerInfoDto.getReservationName(), reservePossibleAlarmCustomerInfoDto.getReservationPhoneNumber());
        if(!optionalReservation.isPresent()) {
            throw new EntityNotFoundException();
        }
    }

    private void sendAlarm(SendMessagePerformanceSeatInfoDto sendMessagePerformanceSeatInfoDto, ReservePossibleAlarmCustomerInfoDto reservePossibleAlarmCustomerInfoDto) {

        //sendAlarmInfo to phoneNumber in sendMessagePerformanceSeatInfoDto
        String reservationPhoneNumber = reservePossibleAlarmCustomerInfoDto.getReservationPhoneNumber();


    }

}
