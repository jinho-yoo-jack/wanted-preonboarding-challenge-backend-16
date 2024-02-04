package com.wanted.preonboarding.domain.reservation.repository;

import static com.wanted.preonboarding.domain.performance.domain.entity.QPerformance.*;
import static com.wanted.preonboarding.domain.reservation.domain.entity.QReservation.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.preonboarding.domain.common.utils.QueryUtils;
import com.wanted.preonboarding.domain.performance.domain.entity.Performance;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceStatus;
import com.wanted.preonboarding.domain.reservation.domain.entity.Reservation;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ReservationRepositoryQueryDsl {

	private final JPAQueryFactory jpaQueryFactory;



	public Page<Reservation> findPage(
		String name,
		String phoneNumber,
		int page,
		int size) {

		PageRequest pageRequest = PageRequest.of(page, size);

		QueryResults<Reservation> results = this.jpaQueryFactory.select(reservation)
			.from(reservation)
			.where(
				reservation.name.eq(name).or(reservation.phoneNumber.eq(phoneNumber)))
			.offset(pageRequest.getOffset())
			.limit(pageRequest.getPageSize())
			.fetchResults(); // TODO

		return new PageImpl<>(results.getResults(), pageRequest, results.getTotal());
	}


}
