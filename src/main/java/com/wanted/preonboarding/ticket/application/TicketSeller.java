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
        PerformanceSeatInfo performanceSeatInfo =
            performanceSeatInfoRepository.findBySeatAndLineAndPerformance_idAndPerformance_round(seat, line, performanceId, round)
            .orElseThrow(EntityNotFoundException::new);
         return PerformanceAndSeatInfo.of(performanceSeatInfo);
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
            performanceSeatInfoRepository.findByIsReserveAndPerformance_id(ReservationStatus.ENABLE.getStatus(), performanceId)
                .orElseThrow(EntityNotFoundException::new);
        return detailInfos.stream().map(SeatInfo::of).toList();
    }

    /**
     * 공연 예매 메서드
     * @param reserveInfo
     * @param reservationId
     * @return boolean
     */
    @Transactional
    public boolean reserveProcess(ReserveInfo reserveInfo, ReservationId reservationId) {
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
        String isReserve = performance.getIsReserve();

        if (isReserve.equalsIgnoreCase(ReservationStatus.ENABLE.getStatus())) {
            // 1. 결제
            int resultPrice = getResultPrice(performance, user);
            Long newBalanceAmount = reserveInfo.getBalanceAmount() - resultPrice;
            updateBalanceAmount(paymentCard, newBalanceAmount);
            // 2. 예매 진행
            reserve(reserveInfo, reservationId, resultPrice, performance, user, seatInfo);
            // 3. 매진 여부 판단
            checkSoldOut(performance, isReserve);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 좌석 예약 메서드
     * @param reserveInfo
     * @param reservationId
     * @param resultPrice
     * @param performance
     * @param user
     * @param seatInfo
     */
    private void reserve(ReserveInfo reserveInfo, ReservationId reservationId, int resultPrice,
        Performance performance, User user, PerformanceSeatInfo seatInfo) {
        Reservation reservation = Reservation.of(reserveInfo, resultPrice, performance, user);
        seatInfo.reserved();
        performanceSeatInfoRepository.save(seatInfo);
        reservationRepository.save(reservation);
        reservationId.setReservationId(reservation.getId());
    }

    /**
     * 공연 매진 여부 판단 메서드
     * @param performance
     *
     */
    private void checkSoldOut(Performance performance, String isReserve) {

        if(isReserve.equals(ReservationStatus.DISABLE.getStatus())){
            performance.reservable();
            performanceRepository.save(performance);
            return;
        }

        long enableReserveCount = performanceSeatInfoRepository.countByIsReserveAndPerformance_id(
            ReservationStatus.ENABLE.getStatus(), performance.getId());
        if(enableReserveCount == 0){
            performance.soldOut();
            performanceRepository.save(performance);
        }
    }

    /**
     * 결제 및 결제 금액 계산 메서드
     * @param performance
     * @param user
     * @return int
     */
    private int getResultPrice(Performance performance, User user) {
        Discount discount = new Discount(performance.getPrice(), user.getBirthday(), performance.getStartDate());
        return discount.discountCalc();
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
    public void reservationCancelProcess(int reservationId){
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
        // 1. 예약 취소
        reservationCancel(seatInfo, reservation);
        // 2. 환불
        Long newBalanceAmount = paymentCard.getBalanceAmount() + reservation.getPrice();
        updateBalanceAmount(paymentCard, newBalanceAmount);
        // 3. 매진 여부 판단
        String isReserve = performance.getIsReserve();
        checkSoldOut(performance, isReserve);



    }

    private void reservationCancel(PerformanceSeatInfo seatInfo, Reservation reservation) {
        seatInfo.cancel();
        performanceSeatInfoRepository.save(seatInfo);
        reservationRepository.delete(reservation);
    }

    private void updateBalanceAmount(PaymentCard paymentCard, Long newBalanceAmount) {
        paymentCard.updateBalanceAmount(newBalanceAmount);
        paymentRepository.save(paymentCard);
    }

}
