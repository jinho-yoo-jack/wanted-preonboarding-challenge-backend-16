package com.wanted.preonboarding.ticket.infrastructure.repository;

import static com.wanted.preonboarding.ticket.domain.entity.QPerformance.performance;
import static com.wanted.preonboarding.ticket.domain.entity.QPerformanceSeatInfo.performanceSeatInfo;
import static com.wanted.preonboarding.ticket.domain.entity.QReservation.reservation;
import static com.wanted.preonboarding.ticket.domain.entity.QWaitingList.waitingList;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.ticket.domain.dto.request.FindReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReserveResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationResponse;
import com.wanted.preonboarding.ticket.domain.entity.WaitingList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ReservationRepositoryCustomImpl implements ReservationRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	@Override
	public FindReserveResponse findReserveInfo(FindReserveRequest request) {
		return queryFactory
			.select(Projections.constructor(FindReserveResponse.class,
				reservation.round,
				performance.name,
				reservation.line,
				reservation.seat,
				performance.id))
			.from(reservation, performance)
			.where(reservation.performanceId.eq(performance.id)
			.and(reservation.name.eq(request.getName()))
			.and(reservation.phoneNumber.eq(request.getPhoneNumber())))
			.fetchOne();
	}

	@Override
	public ReservationResponse cancelReservation(ReservationRequest request) {
		queryFactory
			.delete(performanceSeatInfo)
			.where(performanceSeatInfo.performance.id.eq(request.getId())
			.and(performanceSeatInfo.round.eq(request.getRound()))
			.and(performanceSeatInfo.line.eq(request.getLine()))
			.and(performanceSeatInfo.seat.eq(request.getSeat())))
			.execute();

		WaitingList entity = queryFactory.select(waitingList)
			.from(waitingList)
			.where(waitingList.performanceId.eq(request.getId())
				.and(waitingList.round.eq(request.getRound()))
				.and(waitingList.line.eq(request.getLine())
					.and(waitingList.seat.eq(request.getSeat()))))
			.fetchOne();

		log.info("name => {}, phoneNumber = {}", entity.getName(), entity.getPhoneNumber());
		// 문자메시지 구현
		return ReservationResponse.of(entity);
	}
}
