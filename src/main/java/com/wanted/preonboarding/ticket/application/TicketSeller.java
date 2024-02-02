package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.AlarmRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final AlarmTicket alarmTicket;
    private long totalAmount = 0L;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }

    public boolean ableReserve(String id){
        UUID uuid = UUID.fromString(id);
        boolean result;
        if(performanceRepository.findByIdAndIsReserve(uuid,"enable") == 1)
            result = true;
        else
            result = false;
        return result;
    }

    public ReserveInfo getRevervationInfo(String name, String phoneNumber) {

        ReserveInfo reserve = ReserveInfo.of(reservationRepository.findByNameAndPhoneNumberAndStatus(name, phoneNumber,"reserve"));

        UUID uuid = reserve.getPerformanceId();

        Optional<Performance> performanceOptional = performanceRepository.findById(uuid);
        PerformanceInfo per;

        if (performanceOptional.isPresent()) {
            Performance performance = performanceOptional.get();
            per = PerformanceInfo.of(performance);
        } else {
            throw new RuntimeException("Performance with id " + uuid + " not found");
        }

        BeanUtils.copyProperties(per,reserve);

        return reserve;
    }

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
            // .of() 불변객체유지해줌.
            reservationRepository.save(Reservation.of(reserveInfo));
            return true;

        } else {
            return false;
        }
    }

    public boolean cancel(int id){

        UUID performanceId = reservationRepository.findById(id).orElseThrow(EntityNotFoundException::new).getPerformanceId();
        int result = reservationRepository.updateStatus(id);

        if(result == 1){
            alarmTicket.sendAlarm(performanceId);
            return true;
        } else {
            return false;
        }

    }


}
