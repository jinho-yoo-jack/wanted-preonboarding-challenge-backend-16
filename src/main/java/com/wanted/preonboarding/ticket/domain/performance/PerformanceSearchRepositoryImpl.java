package com.wanted.preonboarding.ticket.domain.performance;

import static com.wanted.preonboarding.ticket.domain.performance.QPerformance.performance;

import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.ticket.domain.base.OrderByNull;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveState;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@RequiredArgsConstructor
@Repository
public class PerformanceSearchRepositoryImpl implements PerformanceSearchRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Performance> findAll(final String reserveState, final Pageable pageable) {
        List<Performance> performances = queryFactory
            .selectFrom(performance)
            .where(isReserveEq(reserveState))
            .orderBy(getOrderSpecifier(pageable, "startDate", performance.startDate))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(performance.count())
            .from(performance)
            .where(isReserveEq(reserveState));

        return PageableExecutionUtils.getPage(performances, pageable, countQuery::fetchOne);
    }

    private BooleanExpression isReserveEq(final String reserveState) {
        if (!StringUtils.hasText(reserveState)) return null;

        ReserveState state = ReserveState.convertToEnum(reserveState);
        return performance.isReserve.eq(state);
    }

    private OrderSpecifier<?> getOrderSpecifier(final Pageable pageable, final String propertyName, final Expression<?> target) {
        Sort sort = pageable.getSort();
        Sort.Order orderFor = sort.getOrderFor(propertyName);

        if (orderFor == null) return OrderByNull.getDefault();

        Order order = Order.valueOf(orderFor.getDirection().name());
        return new OrderSpecifier(order, target);
    }
}
