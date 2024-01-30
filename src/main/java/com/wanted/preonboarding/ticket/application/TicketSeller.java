package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * {@code TicketSeller}는 티켓 판매와 예약 서비스를 제공하는 서비스 클래스입니다.
 *
 * @see PerformanceRepository
 * @see ReservationRepository
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
  private final PerformanceRepository performanceRepository;
  private final ReservationRepository reservationRepository;
  private long totalAmount = 0L;

  /**
   * 예약 가능한 모든 공연 목록을 반환합니다.
   *
   * @return 예약 가능한 공연 정보 목록
   */
  public List<PerformanceInfo> getAllPerformanceInfoList() {
    return performanceRepository.findByIsReserve("enable")
        .stream()
        .map(PerformanceInfo::of)
        .toList();
  }

  /**
   * 지정된 공연 이름에 대한 상세 정보를 반환합니다.
   *
   * @param name 공연 이름
   * @return 공연 정보
   */
  public PerformanceInfo getPerformanceInfoDetail(String name) {
    return PerformanceInfo.of(performanceRepository.findByName(name));
  }

  /**
   * 예매를 처리하고, 성공 여부를 반환합니다.
   *
   * @param reserveInfo 예매 정보
   * @return 예매 성공 시 true, 실패 시 false
   */
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

}
