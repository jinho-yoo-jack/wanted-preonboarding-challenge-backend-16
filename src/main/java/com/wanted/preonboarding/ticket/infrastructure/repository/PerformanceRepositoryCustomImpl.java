package com.wanted.preonboarding.ticket.infrastructure.repository;

import static com.querydsl.core.types.Projections.constructor;
import static com.wanted.preonboarding.ticket.domain.entity.QPerformance.performance;
import static com.wanted.preonboarding.ticket.domain.entity.QPerformanceSeatInfo.performanceSeatInfo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.ticket.domain.dto.request.PerformanceListRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PerformanceRepositoryCustomImpl implements PerformanceRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public List<PerformanceListResponse> findPerformanceAll(PerformanceListRequest request) {
		return queryFactory
			.select(constructor(PerformanceListResponse.class,
				performance.name,
				performanceSeatInfo.line,
				performanceSeatInfo.seat,
				performance.start_date,
				performanceSeatInfo.isReserve))
			.from(performance, performanceSeatInfo)
			.where(performance.id.eq(performanceSeatInfo.performance.id)
			.and(performance.isReserve.eq(request.getSrchPerformanceCond()))
			.and(performanceSeatInfo.isReserve.eq(request.getSrchPerformanceCond())))
			.orderBy(performance.name.asc(), performanceSeatInfo.line.asc(), performanceSeatInfo.seat.asc(), performance.start_date.asc())
			.fetch();
	}
}
