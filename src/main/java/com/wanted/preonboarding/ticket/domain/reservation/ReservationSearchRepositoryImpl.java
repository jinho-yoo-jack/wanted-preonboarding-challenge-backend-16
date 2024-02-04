package com.wanted.preonboarding.ticket.domain.reservation;

import static com.wanted.preonboarding.ticket.domain.performance.QPerformance.performance;
import static com.wanted.preonboarding.ticket.domain.reservation.QReservation.reservation;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.ticket.domain.base.OrderByNull;
import com.wanted.preonboarding.ticket.dto.result.CancelReservationInfo;
import com.wanted.preonboarding.ticket.dto.result.ReservationModel;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ReservationSearchRepositoryImpl implements ReservationSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<ReservationModel> findReservationModel(final String name, final String phoneNumber, final Pageable pageable) {
        List<ReservationModel> reservationModels = queryFactory
            .select(Projections.constructor(ReservationModel.class,
                performance.id.as("performanceId"),
                performance.name.as("performanceName"),
                performance.startDate,
                performance.type,
                reservation.gate,
                reservation.round,
                reservation.line,
                reservation.seat,
                reservation.name,
                reservation.phoneNumber,
                reservation.createdAt.as("reserveDate")
            ))
            .from(reservation)
            .join(performance).on(performance.id.eq(reservation.performanceId))
            .where(
                reservation.name.eq(name),
                reservation.phoneNumber.eq(phoneNumber))
            .orderBy(getOrderSpecifier(pageable, "createAt", reservation.createdAt))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(reservation.count())
            .from(reservation)
            .where(
                reservation.name.eq(name),
                reservation.phoneNumber.eq(phoneNumber));

        return PageableExecutionUtils.getPage(reservationModels, pageable, countQuery::fetchOne);
    }

    @Override
    public Optional<CancelReservationInfo> findInfoForCancel(int reservationId) {
        return Optional.of(
            queryFactory
                .select(Projections.constructor(CancelReservationInfo.class,
                    performance.id.as("performanceId"),
                    performance.name,
                    reservation.round,
                    reservation.line,
                    reservation.seat,
                    performance.startDate,
                    performance.price
                ))
                .from(reservation)
                .join(performance).on(performance.id.eq(reservation.performanceId))
                .where(reservation.id.eq(reservationId))
                .fetchFirst()
        );
    }

    private OrderSpecifier<?> getOrderSpecifier(final Pageable pageable, final String propertyName, final Expression<?> target) {
        Sort sort = pageable.getSort();
        Sort.Order orderFor = sort.getOrderFor(propertyName);

        if (orderFor == null) return OrderByNull.getDefault();

        Order order = Order.valueOf(orderFor.getDirection().name());
        return new OrderSpecifier(order, target);
    }

}
