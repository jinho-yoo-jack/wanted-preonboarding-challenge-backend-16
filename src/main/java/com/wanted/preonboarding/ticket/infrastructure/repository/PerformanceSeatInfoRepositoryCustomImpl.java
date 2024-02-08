package com.wanted.preonboarding.ticket.infrastructure.repository;

import static com.wanted.preonboarding.ticket.domain.entity.QPerformanceSeatInfo.performanceSeatInfo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.QPerformanceSeatInfo;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PerformanceSeatInfoRepositoryCustomImpl implements PerformanceSeatInfoRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public PerformanceSeatInfoRepositoryCustomImpl(JPAQueryFactory queryFactory) {
		this.queryFactory = queryFactory;
	}

	@Override
	public boolean isAvailable(ReserveInfo request) {
		String result = queryFactory
			.select(performanceSeatInfo.isReserve)
			.from(performanceSeatInfo)
			.where(performanceSeatInfo.round.eq(request.getRound())
				.and(performanceSeatInfo.id.eq(Long.valueOf(request.getPerformanceId().toString())))
				.and(performanceSeatInfo.line.eq(String.valueOf(request.getLine())))
				.and(performanceSeatInfo.seat.eq(request.getSeat())))
			.fetchOne();

		return result.equals("enable");
	}
}
