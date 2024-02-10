package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.request.FindReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReserveResponse;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

	@Autowired
	private EntityManager em;

	@Override
	public FindReserveResponse findReserveInfo(FindReserveRequest request) {
		return em.createQuery(
				"select new com.wanted.preonboarding.ticket.domain.dto.response.FindReserveResponse(r.round, p.name, r.line, r.seat, p.id) "
					+ "from Reservation r, Performance p "
					+ "where r.performanceId = p.id "
					+ "and r.name = :name "
					+ "and r.phoneNumber = :phoneNumber", FindReserveResponse.class)
				 .setParameter("name", request.getName())
				 .setParameter("phoneNumber", request.getPhoneNumber())
				 .getSingleResult();
	}
}
