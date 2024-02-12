package com.wanted.preonboarding.ticket.infrastructure.repository;

import static com.wanted.preonboarding.ticket.domain.entity.QPerformance.performance;
import static com.wanted.preonboarding.ticket.domain.entity.QPerformanceSeatInfo.performanceSeatInfo;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserveInfoRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.WaitReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.WaitReservationResponse;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.WaitingList;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PerformanceSeatInfoRepositoryCustomImpl implements PerformanceSeatInfoRepositoryCustom {

	@Autowired
	private EntityManager em;
	private final JPAQueryFactory queryFactory;

	@Override
	public boolean isAvailable(ReserveInfoRequest request) {
		PerformanceSeatInfo entity = queryFactory
			.select(performanceSeatInfo)
			.from(performance, performanceSeatInfo)
			.where(performance.id.eq(performanceSeatInfo.performance.id)
				.and(performanceSeatInfo.round.eq(request.getRound()))
				.and(performanceSeatInfo.line.eq(request.getLine()))
				.and(performanceSeatInfo.seat.eq(request.getSeat()))).fetchOne();

		if (entity.getIsReserve().equalsIgnoreCase("enable")) {
			entity.changeIsReserve();
			return true;
		}

		return false;
	}

	@Override
	public WaitReservationResponse isAvailable(WaitReservationRequest request) {
		PerformanceSeatInfo entity = queryFactory
			.select(performanceSeatInfo)
			.from(performance, performanceSeatInfo)
			.where(performance.id.eq(performanceSeatInfo.performance.id)
				.and(performanceSeatInfo.round.eq(request.getRound()))
				.and(performanceSeatInfo.line.eq(request.getLine()))
				.and(performanceSeatInfo.seat.eq(request.getSeat()))).fetchOne();

		if (entity.getIsReserve().equalsIgnoreCase("disable")) {
			WaitingList waitingList = WaitingList.of(request, entity);
			em.persist(waitingList);
			return WaitReservationResponse.of(waitingList);
		}

		throw new IllegalArgumentException("예약 대기에 실패하였습니다.");
	}
}
