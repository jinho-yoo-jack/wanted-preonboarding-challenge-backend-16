package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.request.FindReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReserveResponse;
import com.wanted.preonboarding.ticket.domain.entity.WaitingList;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationResponse;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j
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

	@Override
	public ReservationResponse cancelReservation(ReservationRequest request) {
		em.createQuery("delete from PerformanceSeatInfo ps "
			+ "where ps.performance.id = :id "
			+ "and ps.round = :round "
			+ "and ps.line = :line "
			+ "and ps.seat = :seat")
			.setParameter("id", request.getId())
			.setParameter("round", request.getRound())
			.setParameter("line", request.getLine())
			.setParameter("seat", request.getSeat())
			.executeUpdate();

		WaitingList entity = em.createQuery("select wl "
				+ "from WaitingList wl "
				+ "where wl.performanceId = :id "
				+ "and wl.round = :round "
				+ "and wl.line = :line "
				+ "and wl.seat = :seat", WaitingList.class)
			.setParameter("id", request.getId())
			.setParameter("round", request.getRound())
			.setParameter("line", request.getLine())
			.setParameter("seat", request.getSeat()).getSingleResult();
		log.info("name => {}, phoneNumber = {}", entity.getName(), entity.getPhoneNumber());
		// 문자메시지 구현
		return ReservationResponse.of(entity);
	}
}
