package com.wanted.preonboarding.domain.reservation.containerservice;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.wanted.preonboarding.domain.common.domain.dto.PageDto;
import com.wanted.preonboarding.domain.payment.service.PaymentService;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceDto;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceHallDto;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceHallSeatDto;
import com.wanted.preonboarding.domain.performance.domain.entity.Performance;
import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHall;
import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHallSeat;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceStatus;
import com.wanted.preonboarding.domain.performance.service.PerformanceHallService;
import com.wanted.preonboarding.domain.performance.service.PerformanceService;
import com.wanted.preonboarding.domain.reservation.domain.dto.ReservationDto;
import com.wanted.preonboarding.domain.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.domain.reservation.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.domain.reservation.domain.request.ReservationCreateRequest;
import com.wanted.preonboarding.domain.reservation.domain.response.ReservationCreateResponse;
import com.wanted.preonboarding.domain.reservation.domain.response.ReservationPageResponse;
import com.wanted.preonboarding.domain.reservation.service.ReservationService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReservationContainerService {

    private final PerformanceService performanceService;
    private final PerformanceHallService performanceHallService;
    private final PaymentService paymentService;
    private final ReservationService reservationService;

    private final ApplicationEventPublisher applicationEventPublisher;



    // TODO lock ( performanceId, hallSeatId )
    @Transactional
    public ReservationCreateResponse createReservation(
        ReservationCreateRequest request) {

        Performance performance = this.performanceService.getPerformance(request.getPerformanceId());
        PerformanceHallSeat hallSeat = this.performanceHallService.getPerformanceHallSeat(request.getHallSeatId());
        PerformanceHall hall = this.performanceHallService.getPerformanceHall(hallSeat.getHallId());

        this.validateReservation(performance, hallSeat);
        this.paymentService.pay(performance.getPrice(), performance.getPrice());

        Reservation reservation = this.reservationService.createReservation(
            performance, hallSeat, request.getName(), request.getPhoneNumber());

        return ReservationCreateResponse.of(
            ReservationDto.of(
                reservation,
                PerformanceDto.of(performance),
                PerformanceHallDto.of(hall),
                PerformanceHallSeatDto.of(hallSeat)));
    }

    // TODO lock ( performanceId, hallSeatId )
    @Transactional
    public void deleteReservation(Long reservationId) {

        Reservation reservation = this.reservationService.getReservation(reservationId);
        Performance performance = this.performanceService.getPerformance(reservation.getPerformanceId());

        // TODO validate is available to cancel

        this.paymentService.cancel(performance.getPrice());
        this.reservationService.deleteReservation(reservation);

        // TODO MQ 로 전환
        this.applicationEventPublisher.publishEvent(
            new ReservationCancelEvent(
                this,
                reservation.getPerformanceId(),
                reservation.getHallSeatId()));
    }

    public ReservationPageResponse getReservationPage(
        String name,
        String phoneNumber,
        int page, int size) {

        Page<Reservation> reservationPage = this.reservationService.getReservationPage(
            name, phoneNumber, page, size);





        return ReservationPageResponse.of(
            PageDto.of(
                this.generateReservationDto(reservationPage.getContent()),
                reservationPage.getTotalElements(),
                reservationPage.hasNext()));
    }

    private void validateReservation(
        Performance performance,
        PerformanceHallSeat hallSeat) {

        if(!Optional.ofNullable(performance.getPerformanceStatus())
			.map(PerformanceStatus::isAvailableToReserveYn)
			.orElse(false)) {

            throw new IllegalArgumentException("예약이 불가능한 상태입니다.");
        }
        if(this.reservationService.isExistsReservation(performance, hallSeat)) {

            throw new IllegalArgumentException("이미 예약된 좌석입니다.");
        }
    }


    private List<ReservationDto> generateReservationDto(List<Reservation> reservationList) {

        Map<UUID, Performance> performanceMap = this.performanceService.getPerformance(
                reservationList.stream()
                    .map(Reservation::getPerformanceId)
                    .collect(Collectors.toList()))
            .stream()
            .collect(Collectors.toMap(
                Performance::getId,
                Function.identity(),
                (a,b)->b));

        Map<Long, PerformanceHallSeat> hallSeatMap = this.performanceHallService.getPerformanceHallSeat(
                reservationList.stream()
                    .map(Reservation::getHallSeatId)
                    .collect(Collectors.toList()))
            .stream()
            .collect(Collectors.toMap(
                PerformanceHallSeat::getId,
                Function.identity(),
                (a,b)->b));

        Map<Long, PerformanceHall> hallMap = this.performanceHallService.getPerformanceHall(
                hallSeatMap.values().stream()
                    .map(PerformanceHallSeat::getHallId)
                    .collect(Collectors.toList()))
            .stream()
            .collect(Collectors.toMap(
                PerformanceHall::getId,
                Function.identity(),
                (a,b)->b));


        return reservationList.stream()
            .map(reservation->{
                Performance performance = performanceMap.get(reservation.getPerformanceId());
                PerformanceHallSeat hallSeat = hallSeatMap.get(reservation.getHallSeatId());
                PerformanceHall hall = hallMap.get(Optional.ofNullable(hallSeat)
                    .map(PerformanceHallSeat::getHallId)
                    .orElse(null));




                return ReservationDto.of(
                    reservation,
                    Optional.ofNullable(performance)
                        .map(PerformanceDto::of)
                        .orElse(null),
                    Optional.ofNullable(hall)
                        .map(PerformanceHallDto::of)
                        .orElse(null),
                    Optional.ofNullable(hallSeat)
                        .map(PerformanceHallSeatDto::of)
                        .orElse(null));
            })
            .collect(Collectors.toList());
    }


}
