package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.discountpolicy.Discount;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceAndSeatInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceDetailInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReservationSearchResult;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveResult;
import com.wanted.preonboarding.ticket.domain.dto.SeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.user.domain.entity.PaymentCard;
import com.wanted.preonboarding.user.domain.entity.User;
import com.wanted.preonboarding.user.infrastructure.PaymentRepository;
import com.wanted.preonboarding.user.infrastructure.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final PaymentRepository paymentRepository;
    private long totalAmount = 0L;

    /**
     * 예약 가능한 공연 리스트 질의
     * @return List<PerformanceInfo>
     */
    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    /**
     * 공연명으로 공연 데이터 질의
     * @param name
     * @return PerformanceInfo
     */
    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    /**
     * 공연 정보 및 좌석 정보 리스트 질의
     * @param id
     * @return PerformanceDetailInfo
     */
    public PerformanceDetailInfo getPerformanceInfoDetailById(String id){
        UUID performanceId = UUID.fromString(id);
        Performance detailInfo = performanceRepository.findById(performanceId)
            .orElseThrow(EntityNotFoundException::new);
        return PerformanceDetailInfo.of(detailInfo);
    }

    public PerformanceAndSeatInfo getPerformanceAndSeatInfo(String id, int round, int seat, char line){
        UUID performanceId = UUID.fromString(id);
         return PerformanceAndSeatInfo.of(performanceSeatInfoRepository.findBySeatAndLineAndPerformance_idAndPerformance_round(seat, line, performanceId, round)
             .orElseThrow(EntityNotFoundException::new));
    }



    /**
     * 좌석 정보 리스트 질의 메서드
     * @param id
     * @return List<SeatInfo>
     */
    public List<SeatInfo> getPerformanceSeatInfoDetailListById(String id){
        UUID performanceId = UUID.fromString(id);
        List<PerformanceSeatInfo> detailInfos =
            performanceSeatInfoRepository.findByIsReserveAndPerformance_id("enable", performanceId)
                .orElseThrow(EntityNotFoundException::new);
        return detailInfos.stream().map(SeatInfo::of).toList();
    }

    public boolean reserve(ReserveInfo reserveInfo, Integer reservationId) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());

        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.getReferenceByPhoneNumber(reserveInfo.getPhoneNumber());
        PaymentCard paymentCard = user.getPaymentCards()
            .stream()
            .filter(card -> card.getId().equals(user.getDefaultPaymentCode()))
            .findAny()
            .orElseThrow(EntityNotFoundException::new);
        String enableReserve = info.getIsReserve();

        if (enableReserve.equalsIgnoreCase("enable")) {
            // 1. 결제
            Discount discount = new Discount(info.getPrice(), user.getBirthday(), info.getStart_date());
            int resultPrice = discount.discountCalc();
            paymentCard.updateBalanceAmount(reserveInfo.getBalanceAmount() - resultPrice);
            // 2. 예매 진행
            Reservation reservation = Reservation.of(reserveInfo, info, user);
            reservationRepository.save(reservation);
            paymentRepository.save(paymentCard);
            reservationId = reservation.getId();
            return true;
        } else {
            return false;
        }
    }

    public ReserveResult getReservationInfo(int reservationId){
        return ReserveResult.of(reservationRepository.findById(reservationId)
            .orElseThrow(EntityNotFoundException::new));
    }

    public List<ReservationSearchResult> searchReservation(String reservationName, String phoneNumber){
        return reservationRepository.findByUser_phoneNumberAndUser_name(phoneNumber, reservationName).stream()
            .map(ReservationSearchResult::of).toList();
    }

}
