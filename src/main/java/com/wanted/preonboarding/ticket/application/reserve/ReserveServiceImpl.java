package com.wanted.preonboarding.ticket.application.reserve;

import com.wanted.preonboarding.ticket.application.discount.DiscountManager;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.reservation.ReservationRepository;
import com.wanted.preonboarding.ticket.dto.request.reservation.ReservationRequest;
import com.wanted.preonboarding.ticket.dto.response.reservation.ReservationInfo;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReserveServiceImpl implements ReserveService {

    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final DiscountManager discountManager;

    /*
    * 예약 가능한 좌석인지 확인
    * 할인 정책 적용
    * 예약 데이터 추가
    * !-- 동시성 처리 추가
    * */
    @Transactional
    @Override
    public ReservationInfo reserve(final ReservationRequest request, final LocalDateTime requestTime) {

        return null;
    }
}
