package com.wanted.preonboarding.ticket.infrastructure.repository;

import static com.querydsl.core.types.Projections.constructor;
import static com.wanted.preonboarding.ticket.domain.entity.QPerformance.performance;
import static com.wanted.preonboarding.ticket.domain.entity.QPerformanceSeatInfo.performanceSeatInfo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.ticket.domain.dto.request.PerformanceListRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceListResponse;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class PerformanceRepositoryCustomImpl implements PerformanceRepositoryCustom {

	@Autowired
	private EntityManager em;
	private final JPAQueryFactory queryFactory;

	public PerformanceRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}

//	@Override
//	public List<PerformanceListResponse> findPerformanceAll(PerformanceListRequest request) {
//		return em
//			.createQuery("select new com.wanted.preonboarding.ticket.domain.dto.response.PerformanceListResponse(p.name, ps.line, ps.seat, p.start_date, ps.isReserve) "
//			+ "from Performance p, PerformanceSeatInfo ps "
//			+ "where p.id = ps.performance.id "
//			+ "and p.isReserve = :isReserve "
//			+ "and ps.isReserve = :isReserve "
//			+ "order by p.name, ps.line, ps.seat, p.start_date", PerformanceListResponse.class)
//			.setParameter("isReserve", request.getSrchPerformanceCond())
//			.getResultList();
//	}

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
