package com.wanted.preonboarding.reservation.infrastructure.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.reservation.application.dto.QReservationResponse;
import com.wanted.preonboarding.reservation.application.dto.ReservationResponse;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.wanted.preonboarding.performance.domain.entity.QPerformance.*;
import static com.wanted.preonboarding.reservation.domain.entity.QReservation.*;

public class ReservationCustomRepositoryImpl implements ReservationCustomRepository{

    private final JPAQueryFactory query;

    public ReservationCustomRepositoryImpl(final EntityManager em) {
        this.query = new JPAQueryFactory(em);
    }

    @Override
    public List<ReservationResponse> findReservationResponseByUserInfo(final UserInfo userInfo) {
        return query.select(new QReservationResponse(
                    performance.round,
                    performance.name,
                    performance.id,
                    reservation.seatInfo,
                    reservation.userInfo))
                .from(reservation)
                .leftJoin(performance)
                .on(performance.id.eq(reservation.performanceId.value))
                .where(reservation.userInfo.eq(userInfo))
                .stream().toList();
    }

    @Override
    public Optional<ReservationResponse> findReservationResponseById(int reservationId) {
        return Optional.ofNullable(
                query.select(new QReservationResponse(
                                performance.round,
                                performance.name,
                                performance.id,
                                reservation.seatInfo,
                                reservation.userInfo))
                        .from(reservation)
                        .leftJoin(performance)
                        .on(performance.id.eq(reservation.performanceId.value))
                    .where(reservation.id.eq(reservationId))
                    .fetchFirst()
        );
    }
}
