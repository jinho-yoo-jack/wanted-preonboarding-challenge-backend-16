package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.discountpolicy.Discount;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceAndSeatInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceDetailInfo;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReservationId;
import com.wanted.preonboarding.ticket.domain.dto.ReservationSearchResult;
import com.wanted.preonboarding.ticket.domain.dto.ReservationStatus;
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
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(readOnly = true)
    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserveAndStartDateAfter(ReservationStatus.ENABLE.getStatus(),
                LocalDateTime.now())
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    /**
     * 예약 불가능한 공연 리스트 질의
     * @return List<PerformanceInfo>
     * @return List<PerformanceInfo>
     */
    @Transactional(readOnly = true)
    public List<PerformanceInfo> getAllPerformanceUnreservaleList(){
        return performanceRepository.findByIsReserveAndStartDateAfter(ReservationStatus.DISABLE.getStatus(),
                LocalDateTime.now())
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    /**
     * 공연명으로 공연 데이터 질의
     * @param name
     * @return PerformanceInfo
     */
    @Transactional(readOnly = true)
    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    /**
     * 공연 상세 정보 및 좌석 정보 리스트 질의
     * @param id
     * @return PerformanceDetailInfo
     */
    @Transactional(readOnly = true)
    public PerformanceDetailInfo getPerformanceInfoDetailById(String id){
        UUID performanceId = UUID.fromString(id);
        Performance detailInfo = performanceRepository.findById(performanceId)
            .orElseThrow(EntityNotFoundException::new);
        return PerformanceDetailInfo.of(detailInfo);
    }

    /**
     * 공연 상세 정보 및 좌석 정보 리스트 질의
     * @param id
     * @param round
     * @param seat
     * @param line
     * @return PerformanceAndSeatInfo
     */
    @Transactional(readOnly = true)
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
    @Transactional(readOnly = true)
    public List<SeatInfo> getPerformanceSeatInfoDetailListById(String id){
        UUID performanceId = UUID.fromString(id);
        List<PerformanceSeatInfo> detailInfos =
            performanceSeatInfoRepository.findByIsReserveAndPerformance_id("enable", performanceId)
                .orElseThrow(EntityNotFoundException::new);
        return detailInfos.stream().map(SeatInfo::of).toList();
    }

    /**
     * 공연 예매 메서드
     * @param reserveInfo
     * @param reservationId
     * @return
     */
    @Transactional
    public boolean reserve(ReserveInfo reserveInfo, ReservationId reservationId) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());

        Performance performance = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);
        User user = userRepository.getReferenceByPhoneNumber(reserveInfo.getPhoneNumber());
        PaymentCard paymentCard = user.getPaymentCards()
            .stream()
            .filter(card -> card.getId().equals(user.getDefaultPaymentCode()))
            .findAny()
            .orElseThrow(EntityNotFoundException::new);
        PerformanceSeatInfo seatInfo = performanceSeatInfoRepository.findBySeatAndLineAndPerformance_idAndPerformance_round(reserveInfo.getSeat(),
            reserveInfo.getLine(), performance.getId(), performance.getRound()).orElseThrow(EntityNotFoundException::new);
        String enableReserve = performance.getIsReserve();

        if (enableReserve.equalsIgnoreCase(ReservationStatus.ENABLE.getStatus())) {
            // 1. 결제
            Discount discount = new Discount(performance.getPrice(), user.getBirthday(), performance.getStartDate());
            int resultPrice = discount.discountCalc();
            paymentCard.updateBalanceAmount(reserveInfo.getBalanceAmount() - resultPrice);
            // 2. 예매 진행
            Reservation reservation = Reservation.of(reserveInfo, resultPrice, performance, user);
            seatInfo.reserved();
            paymentRepository.save(paymentCard);
            reservationRepository.save(reservation);
            performanceSeatInfoRepository.save(seatInfo);
            long enableReserveCount = performanceSeatInfoRepository.countByIsReserveAndPerformance_id(
                ReservationStatus.ENABLE.getStatus(), performance.getId());
            if(enableReserveCount == 0){
                performance.soldOut();
                performanceRepository.save(performance);
            }
            reservationId.setReservationId(reservation.getId());
            return true;
        } else {
            return false;
        }
    }

    /**
     * 예매 정보 질의 메서드
     * @param reservationId
     * @return
     */
    @Transactional(readOnly = true)
    public ReserveResult getReservationInfo(int reservationId){
        return ReserveResult.of(reservationRepository.findById(reservationId)
            .orElseThrow(EntityNotFoundException::new));
    }

    /**
     * 예매 정보 리스트 질의 메서드
     * @param reservationName
     * @param phoneNumber
     * @return
     */
    @Transactional(readOnly = true)
    public List<ReservationSearchResult> searchReservation(String reservationName, String phoneNumber){
        return reservationRepository.findByUser_phoneNumberAndUser_name(phoneNumber, reservationName).stream()
            .map(ReservationSearchResult::of).toList();
    }

    /**
     * 예매 취소 및 환불 메서드
     * @param reservationId
     */
    @Transactional
    public void reservationCancel(int reservationId){
        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(EntityNotFoundException::new);
        Performance performance = reservation.getPerformance();
        User user = reservation.getUser();
        PaymentCard paymentCard = user.getPaymentCards()
            .stream()
            .filter(card -> card.getId().equals(user.getDefaultPaymentCode()))
            .findAny()
            .orElseThrow(EntityNotFoundException::new);
        PerformanceSeatInfo seatInfo = performanceSeatInfoRepository
            .findBySeatAndLineAndPerformance_idAndPerformance_round(reservation.getSeat(), reservation.getLine(), performance
            .getId(), performance.getRound())
            .orElseThrow(EntityNotFoundException::new);
        seatInfo.cancel();
        paymentCard.updateBalanceAmount(paymentCard.getBalanceAmount() + reservation.getPrice());

        if(performance.getIsReserve().equals(ReservationStatus.DISABLE.getStatus())){
            performance.reservable();
            performanceRepository.save(performance);
        }
        reservationRepository.delete(reservation);
        performanceSeatInfoRepository.save(seatInfo);
        paymentRepository.save(paymentCard);
    }

}
