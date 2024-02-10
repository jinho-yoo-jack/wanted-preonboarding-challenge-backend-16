package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.request.PerformanceListRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceListResponse;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class PerformanceRepositoryCustomImpl implements PerformanceRepositoryCustom {

	@Autowired
	private EntityManager em;

	@Override
	public List<PerformanceListResponse> findPerformanceAll(PerformanceListRequest request) {
		return em
			.createQuery("select new com.wanted.preonboarding.ticket.domain.dto.response.PerformanceListResponse(p.name, ps.line, ps.seat, p.start_date, ps.isReserve) "
			+ "from Performance p, PerformanceSeatInfo ps "
			+ "where p.id = ps.performance.id "
			+ "and p.isReserve = :isReserve "
			+ "and ps.isReserve = :isReserve "
			+ "order by p.name, ps.line, ps.seat, p.start_date", PerformanceListResponse.class)
			.setParameter("isReserve", request.getSrchPerformanceCond())
			.getResultList();
	}
}
