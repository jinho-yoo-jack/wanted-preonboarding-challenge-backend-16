package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.ticket.application.dto.PerformanceDto;
import com.wanted.preonboarding.ticket.application.dto.PerformanceSearchCondition;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import com.wanted.preonboarding.ticket.domain.entity.QPerformance;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryCustomImpl implements PerformanceRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private final QPerformance performance = QPerformance.performance;

    @Override
    public Page<PerformanceDto> searchPage(PerformanceSearchCondition condition, Pageable pageable) {
        List<PerformanceDto> content = queryFactory
                .select(Projections.constructor(PerformanceDto.class,
                                performance.id.as("performanceId"),
                        performance.name,
                        performance.round,
                        performance.type,
                        performance.startDate,
                        performance.isReservable)
                )
                .from(performance)
                .where(nameContains(condition.getName()),
                        roundEq(condition.getRound()),
                        minPriceGoe(condition.getMinPrice()),
                        maxPriceLoe(condition.getMaxPrice()),
                        typeEq(condition.getType()),
                        isReservableEq(condition.getIsReservable()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        int total = queryFactory
                .select(performance)
                .from(performance)
                .where(nameContains(condition.getName()),
                        roundEq(condition.getRound()),
                        minPriceGoe(condition.getMinPrice()),
                        maxPriceLoe(condition.getMaxPrice()),
                        typeEq(condition.getType()),
                        isReservableEq(condition.getIsReservable()))
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression nameContains(String name) {
        System.out.println("--------------");
        System.out.println(name);
        System.out.println(StringUtils.hasText(name));
        return StringUtils.hasText(name) ? performance.name.contains(name) : null;
    }

    private BooleanExpression roundEq(Integer round) {
        return round != null ? performance.round.eq(round) : null;
    }

    private BooleanExpression minPriceGoe(Integer minPrice) {
        return minPrice != null ? performance.price.goe(minPrice) : null;
    }

    private BooleanExpression maxPriceLoe(Integer maxPrice) {
        return maxPrice != null ? performance.price.loe(maxPrice) : null;
    }

    private BooleanExpression typeEq(PerformanceType type) {
        return type != null ? performance.type.eq(type) : null;
    }

    private BooleanExpression isReservableEq(Boolean isReservable) {
        return isReservable != null ? performance.isReservable.eq(isReservable) : null;
    }
}
