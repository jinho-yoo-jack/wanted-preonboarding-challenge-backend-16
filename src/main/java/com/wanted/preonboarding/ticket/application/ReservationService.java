package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.account.application.event.PaymentEventPublisher;
import com.wanted.preonboarding.account.application.event.dto.PaymentInfo;
import com.wanted.preonboarding.account.domain.vo.Money;
import com.wanted.preonboarding.ticket.application.dto.request.CreateReserveServiceRequest;
import com.wanted.preonboarding.ticket.application.dto.request.FindReserveServiceRequest;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
import com.wanted.preonboarding.ticket.application.mapper.PerformanceReader;
import com.wanted.preonboarding.ticket.application.mapper.PerformanceSeatInfoReader;
import com.wanted.preonboarding.ticket.application.mapper.ReservationReader;
import com.wanted.preonboarding.ticket.application.strategy.DiscountConditionInfo;
import com.wanted.preonboarding.ticket.application.strategy.DiscountPolicy;
import com.wanted.preonboarding.ticket.application.validator.ReservationValidator;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.user.User;
import com.wanted.preonboarding.user.application.mapper.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ReservationReader reservationReader;

    private final PerformanceReader performanceReader;
    private final PerformanceSeatInfoReader performanceSeatInfoReader;
    private final UserReader userReader;

    private final ReservationValidator reservationValidator;
    private final DiscountPolicy discountPolicy;

    private final PaymentEventPublisher paymentEventPublisher;

    @Transactional
    public ReserveResponse reserve(CreateReserveServiceRequest request) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoReader.findById(request.performanceSeatInfoId());
        Performance performance = performanceReader.findById(performanceSeatInfo.getPerformanceId());
        reservationValidator.validatePossibleReserve(performance, performanceSeatInfo);

        Money discountedMoney = getDiscountedMoney(performance);

        User user = userReader.findById(request.userId());
        paymentEventPublisher.publish(PaymentInfo.of(user, discountedMoney));

        Reservation reservation = reservationRepository.save(Reservation.createReservation(user, performanceSeatInfo));

        return ReserveResponse.of(performance, reservation);
    }

    private Money getDiscountedMoney(Performance performance) {
        DiscountConditionInfo discountConditionInfo = DiscountConditionInfo.builder()
                .round(performance.getRound())
                .build();

        Money originMoney = Money.createMoney(performance.getPrice().getAmount());

        return discountPolicy.calculateDiscountedAmount(originMoney, discountConditionInfo);
    }

    public List<ReserveResponse> findReserve(FindReserveServiceRequest request) {
        return reservationReader.findReservationInfoOfUser(request.reservationName(), request.reservationPhoneNumber())
                .stream()
                .map(ReserveResponse::from)
                .toList();
    }
}
