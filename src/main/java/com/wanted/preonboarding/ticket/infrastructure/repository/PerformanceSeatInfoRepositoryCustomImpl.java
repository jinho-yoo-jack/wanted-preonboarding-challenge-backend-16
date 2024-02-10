package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.request.ReserveInfoRequest;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PerformanceSeatInfoRepositoryCustomImpl implements PerformanceSeatInfoRepositoryCustom {

	@Autowired
	private EntityManager em;

	@Override
	public boolean isAvailable(ReserveInfoRequest request) {
		PerformanceSeatInfo entity = em.createQuery(
				"select ps "
					+ "from Performance p, PerformanceSeatInfo ps "
					+ "where p.id = ps.performance.id "
					+ "and ps.round = :round "
					+ "and ps.line = :line "
					+ "and ps.seat = :seat", PerformanceSeatInfo.class)
			.setParameter("round", request.getRound())
			.setParameter("line", request.getLine())
			.setParameter("seat", request.getSeat())
			.getSingleResult();
		if (entity.getIsReserve().equalsIgnoreCase("enable")) {
			entity.changeIsReserve();
			return true;
		}
		return false;
	}
}
